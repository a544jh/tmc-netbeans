package fi.helsinki.cs.tmc.utilities;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Cancellable;
import org.openide.util.RequestProcessor;

/**
 * A future that calls {@link BgTaskListener} when finished and
 * displays a progress indicator in NetBeans. It cancels by
 * sending a thread interrupt unless the given {@link Callable} is
 * also {@link Cancellable}.
 */
public class BgTask<V> implements CancellableCallable<V> {
    
    private static RequestProcessor defaultRequestProcessor =
            new RequestProcessor("BgTask processor", 5, true);
    
    
    private String label;
    private BgTaskListener listener;
    private Callable<V> callable;
    private ProgressHandle progressHandle;
    
    private final Object cancelLock = new Object();
    private boolean cancelled;
    private Thread executingThread;
    
    public BgTask(String label, BgTaskListener<V> listener, Callable<V> callable) {
        this.label = label;
        this.listener = listener;
        this.callable = callable;
        this.progressHandle = null;
    }

    public BgTask<V> withProgressHandle(ProgressHandle handle) {
        this.progressHandle = handle;
        return this;
    }
    
    public Future<V> start() {
        return defaultRequestProcessor.submit(this);
    }
    
    @Override
    public V call() {
        synchronized (cancelLock) {
            if (cancelled) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        listener.backgroundTaskCancelled();
                    }
                });
                return null;
            } else {
                executingThread = Thread.currentThread();
            }
        }
        
        if (progressHandle == null) {
            progressHandle = ProgressHandleFactory.createSystemHandle(label, this);
        }
        
        progressHandle.start();
        try {
            final V result = callable.call();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    listener.backgroundTaskReady(result);
                }
            });
            return result;
        } catch (InterruptedException e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    listener.backgroundTaskCancelled();
                }
            });
            return null;
        } catch (final Throwable t) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    listener.backgroundTaskFailed(t);
                }
            });
            return null;
        } finally {
            synchronized (cancelLock) {
                executingThread = null;
            }
            progressHandle.finish();
        }
    }

    @Override
    public boolean cancel() {
        Thread theThread = null;
        synchronized (cancelLock) {
            cancelled = true;
            if (callable instanceof Cancellable) {
                return ((Cancellable)callable).cancel();
            } else {
                if (executingThread != null) {
                    executingThread.interrupt();
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}