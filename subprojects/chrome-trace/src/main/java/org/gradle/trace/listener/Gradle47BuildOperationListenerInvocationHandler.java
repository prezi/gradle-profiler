package org.gradle.trace.listener;

import org.gradle.api.internal.TaskInternal;
import org.gradle.internal.operations.BuildOperationDescriptor;
import org.gradle.internal.operations.OperationFinishEvent;
import org.gradle.internal.operations.OperationStartEvent;
import org.gradle.trace.TraceResult;

public class Gradle47BuildOperationListenerInvocationHandler extends BuildOperationListenerInvocationHandler {

    public Gradle47BuildOperationListenerInvocationHandler(TraceResult traceResult) {
        super(traceResult);
    }

    protected String getName(Object operation) {
        BuildOperationDescriptor operationDescriptor = (BuildOperationDescriptor) operation;
        TaskInternal task = getTask(operation);
        if (task == null) {
            return operationDescriptor.getDisplayName() + " (" + operationDescriptor.getId() + ")";
        } else {
            return task.getPath();
        }
    }

    protected TaskInternal getTask(Object operation) {
        Object details = call(operation, "getDetails");
        if (details != null && details.getClass().getName().equals("org.gradle.api.execution.internal.ExecuteTaskBuildOperationDetails")) {
            return (TaskInternal) call(details, "getTask");
        }
        return null;
    }

    @Override
    protected long getStartTime(Object startEvent) {
        return ((OperationStartEvent) startEvent).getStartTime();
    }

    @Override
    protected long getEndTime(Object result) {
        return ((OperationFinishEvent) result).getEndTime();
    }

    protected boolean isTaskCacheable(TaskInternal task) {
        return task.getState().getTaskOutputCaching().isEnabled();
    }
}
