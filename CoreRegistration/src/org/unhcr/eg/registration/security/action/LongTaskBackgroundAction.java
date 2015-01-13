/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.action;

/**
 *
 * @author UNHCRUser
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.RequestProcessor;
import org.openide.util.TaskListener;

public abstract class LongTaskBackgroundAction implements ActionListener {

    public static final String TASKS_POOL_NAME = "background tasks";
    //The RequestProcessor has to have  allowInterrupt set to true!!
    private final static RequestProcessor RP = new RequestProcessor(TASKS_POOL_NAME, 1, true);
    private final static Logger LOG = Logger.getLogger(LongTaskBackgroundAction.class.getName());
    private String localTaskName;
    private RequestProcessor.Task theTask;

    public RequestProcessor.Task getTheTask() {
        return theTask;
    }

    public LongTaskBackgroundAction(String localTaskName) {
        this.localTaskName = localTaskName;
    }

    protected abstract void mainAction();

    @Override
    public void actionPerformed(ActionEvent e) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mainAction();             // Call save() method
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.log(Level.SEVERE, "something may be wrong", e);
                    theTask.cancel();
                }

            }
        };

        theTask = RP.create(runnable);

        final ProgressHandle ph;
        ph = ProgressHandleFactory.createHandle(localTaskName, theTask);

        theTask.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(org.openide.util.Task task) {
                ph.switchToIndeterminate();
                //make sure that we get rid of the ProgressHandle
                //when the task is finished
                ph.finish();
            }
        });

        //start the progresshandle the progress UI will show 500s after
        ph.start();

        //this actually start the task
        theTask.schedule(0);
    }
}
