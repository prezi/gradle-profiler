package org.gradle.profiler.mutations;

import java.io.File;

public class ApplyNonAbiChangeToSourceFileMutator extends AbstractDelegateFileMutator {

    public ApplyNonAbiChangeToSourceFileMutator(File sourceFile) {
        super(sourceFile);
    }

    @Override
    protected AbstractFileChangeMutator getFileChangeMutator(File sourceFile) {
        if (sourceFile.getName().endsWith(".kt")) {
            return new ApplyNonAbiChangeToKotlinSourceFileMutator(sourceFile);
        } else if (sourceFile.getName().endsWith(".java")) {
            return new ApplyNonAbiChangeToJavaSourceFileMutator(sourceFile);
        } else if (sourceFile.getName().endsWith(".ts")) {
            return new ApplyChangeToTsSourceFileMutator(sourceFile);
        } else {
            throw new IllegalArgumentException("Can only modify Java or Kotlin source files");
        }
    }
}
