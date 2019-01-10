package org.gradle.profiler.mutations;

import java.io.File;

public class ApplyChangeToTsSourceFileMutator extends AbstractFileChangeMutator {

    public ApplyChangeToTsSourceFileMutator(File sourceFile) {
        super(sourceFile);
    }

    @Override
    protected void applyChangeTo(StringBuilder text) {
        text.append("export const _").append(getUniqueText()).append(": number = 1;");
    }
}
