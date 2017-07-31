/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.nativeplatform.toolchain.internal.gcc;

import org.gradle.language.base.internal.compile.Compiler;
import org.gradle.nativeplatform.internal.CompilerOutputFileNamingSchemeFactory;
import org.gradle.nativeplatform.internal.LinkerSpec;
import org.gradle.nativeplatform.internal.StaticLibraryArchiverSpec;
import org.gradle.nativeplatform.platform.internal.OperatingSystemInternal;
import org.gradle.nativeplatform.toolchain.internal.AbstractPlatformToolProvider;
import org.gradle.nativeplatform.toolchain.internal.CommandLineToolContext;
import org.gradle.nativeplatform.toolchain.internal.DefaultMutableCommandLineToolContext;
import org.gradle.nativeplatform.toolchain.internal.MutableCommandLineToolContext;
import org.gradle.nativeplatform.toolchain.internal.NativeCompilerFactory;
import org.gradle.nativeplatform.toolchain.internal.ToolType;
import org.gradle.nativeplatform.toolchain.internal.compilespec.AssembleSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.CCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.CPCHCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.CppCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.CppPCHCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.ObjectiveCCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.ObjectiveCPCHCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.ObjectiveCppCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.compilespec.ObjectiveCppPCHCompileSpec;
import org.gradle.nativeplatform.toolchain.internal.tools.GccCommandLineToolConfigurationInternal;
import org.gradle.nativeplatform.toolchain.internal.tools.ToolRegistry;
import org.gradle.nativeplatform.toolchain.internal.tools.ToolSearchPath;

import java.io.File;

class GccPlatformToolProvider extends AbstractPlatformToolProvider {
    private final ToolSearchPath toolSearchPath;
    private final NativeCompilerFactory compilerFactory;
    private final ToolRegistry toolRegistry;
    private final CompilerOutputFileNamingSchemeFactory compilerOutputFileNamingSchemeFactory;
    private final boolean useCommandFile;

    GccPlatformToolProvider(NativeCompilerFactory compilerFactory, OperatingSystemInternal targetOperatingSystem, ToolSearchPath toolSearchPath, ToolRegistry toolRegistry, CompilerOutputFileNamingSchemeFactory compilerOutputFileNamingSchemeFactory, boolean useCommandFile) {
        super(targetOperatingSystem);
        this.compilerFactory = compilerFactory;
        this.toolRegistry = toolRegistry;
        this.toolSearchPath = toolSearchPath;
        this.compilerOutputFileNamingSchemeFactory = compilerOutputFileNamingSchemeFactory;
        this.useCommandFile = useCommandFile;
    }

    @Override
    protected Compiler<CppCompileSpec> createCppCompiler() {
        GccCommandLineToolConfigurationInternal cppCompilerTool = toolRegistry.getTool(ToolType.CPP_COMPILER);
        CppCompiler compiler = new CppCompiler(compilerOutputFileNamingSchemeFactory, context(cppCompilerTool), getObjectFileExtension(), useCommandFile);
        File compilerExe = commandLineTool(cppCompilerTool);
        return compilerFactory.incrementalAndParallelCompiler(compiler, cppCompilerTool.getToolType().getToolName(), compilerExe, NativeCompilerFactory.CPreprocessorDialect.Gcc, getObjectFileExtension());
    }

    @Override
    protected Compiler<CppPCHCompileSpec> createCppPCHCompiler() {
        GccCommandLineToolConfigurationInternal cppCompilerTool = toolRegistry.getTool(ToolType.CPP_COMPILER);
        CppPCHCompiler compiler = new CppPCHCompiler(compilerOutputFileNamingSchemeFactory, context(cppCompilerTool), getPCHFileExtension(), useCommandFile);
        File compilerExe = commandLineTool(cppCompilerTool);
        return compilerFactory.incrementalAndParallelCompiler(compiler, cppCompilerTool.getToolType().getToolName(), compilerExe, NativeCompilerFactory.CPreprocessorDialect.Gcc, getPCHFileExtension());
    }

    @Override
    protected Compiler<CCompileSpec> createCCompiler() {
        GccCommandLineToolConfigurationInternal cCompilerTool = toolRegistry.getTool(ToolType.C_COMPILER);
        CCompiler compiler = new CCompiler(compilerOutputFileNamingSchemeFactory, context(cCompilerTool), getObjectFileExtension(), useCommandFile);
        File compilerExe = commandLineTool(cCompilerTool);
        return compilerFactory.incrementalAndParallelCompiler(compiler, cCompilerTool.getToolType().getToolName(), compilerExe, NativeCompilerFactory.CPreprocessorDialect.Gcc, getObjectFileExtension());
    }

    @Override
    protected Compiler<CPCHCompileSpec> createCPCHCompiler() {
        GccCommandLineToolConfigurationInternal cCompilerTool = toolRegistry.getTool(ToolType.C_COMPILER);
        CPCHCompiler compiler = new CPCHCompiler(compilerOutputFileNamingSchemeFactory, context(cCompilerTool), getPCHFileExtension(), useCommandFile);
        File compilerExe = commandLineTool(cCompilerTool);
        return compilerFactory.incrementalAndParallelCompiler(compiler, cCompilerTool.getToolType().getToolName(), compilerExe, NativeCompilerFactory.CPreprocessorDialect.Gcc, getPCHFileExtension());
    }

    @Override
    protected Compiler<ObjectiveCppCompileSpec> createObjectiveCppCompiler() {
        GccCommandLineToolConfigurationInternal objectiveCppCompilerTool = toolRegistry.getTool(ToolType.OBJECTIVECPP_COMPILER);
        ObjectiveCppCompiler compiler = new ObjectiveCppCompiler(compilerOutputFileNamingSchemeFactory, context(objectiveCppCompilerTool), getObjectFileExtension(), useCommandFile);
        File compilerExe = commandLineTool(objectiveCppCompilerTool);
        return compilerFactory.incrementalAndParallelCompiler(compiler, objectiveCppCompilerTool.getToolType().getToolName(), compilerExe, NativeCompilerFactory.CPreprocessorDialect.Gcc, getObjectFileExtension());
    }

    @Override
    protected Compiler<ObjectiveCppPCHCompileSpec> createObjectiveCppPCHCompiler() {
        GccCommandLineToolConfigurationInternal objectiveCppCompilerTool = toolRegistry.getTool(ToolType.OBJECTIVECPP_COMPILER);
        ObjectiveCppPCHCompiler compiler = new ObjectiveCppPCHCompiler(compilerOutputFileNamingSchemeFactory, context(objectiveCppCompilerTool), getPCHFileExtension(), useCommandFile);
        File compilerExe = commandLineTool(objectiveCppCompilerTool);
        return compilerFactory.incrementalAndParallelCompiler(compiler, objectiveCppCompilerTool.getToolType().getToolName(), compilerExe, NativeCompilerFactory.CPreprocessorDialect.Gcc, getPCHFileExtension());
    }

    @Override
    protected Compiler<ObjectiveCCompileSpec> createObjectiveCCompiler() {
        GccCommandLineToolConfigurationInternal objectiveCCompilerTool = toolRegistry.getTool(ToolType.OBJECTIVEC_COMPILER);
        ObjectiveCCompiler compiler = new ObjectiveCCompiler(compilerOutputFileNamingSchemeFactory, context(objectiveCCompilerTool), getObjectFileExtension(), useCommandFile);
        File compilerExe = commandLineTool(objectiveCCompilerTool);
        return compilerFactory.incrementalAndParallelCompiler(compiler, objectiveCCompilerTool.getToolType().getToolName(), compilerExe, NativeCompilerFactory.CPreprocessorDialect.Gcc, getObjectFileExtension());
    }

    @Override
    protected Compiler<ObjectiveCPCHCompileSpec> createObjectiveCPCHCompiler() {
        GccCommandLineToolConfigurationInternal objectiveCCompilerTool = toolRegistry.getTool(ToolType.OBJECTIVEC_COMPILER);
        ObjectiveCPCHCompiler compiler = new ObjectiveCPCHCompiler(compilerOutputFileNamingSchemeFactory, context(objectiveCCompilerTool), getPCHFileExtension(), useCommandFile);
        File compilerExe = commandLineTool(objectiveCCompilerTool);
        return compilerFactory.incrementalAndParallelCompiler(compiler, objectiveCCompilerTool.getToolType().getToolName(), compilerExe, NativeCompilerFactory.CPreprocessorDialect.Gcc, getPCHFileExtension());
    }

    @Override
    protected Compiler<AssembleSpec> createAssembler() {
        GccCommandLineToolConfigurationInternal assemblerTool = toolRegistry.getTool(ToolType.ASSEMBLER);
        // Disable command line file for now because some custom assemblers don't understand the same arguments as GCC.
        Assembler assembler = new Assembler(compilerOutputFileNamingSchemeFactory, context(assemblerTool), getObjectFileExtension(), false);
        File assemblerExe = commandLineTool(assemblerTool);
        return compilerFactory.compiler(assembler, assemblerTool.getToolType().getToolName(), assemblerExe);
    }

    @Override
    protected Compiler<LinkerSpec> createLinker() {
        GccCommandLineToolConfigurationInternal linkerTool = toolRegistry.getTool(ToolType.LINKER);
        GccLinker linker = new GccLinker(context(linkerTool), useCommandFile);
        File linkerExe = commandLineTool(linkerTool);
        return compilerFactory.compiler(linker, linkerTool.getToolType().getToolName(), linkerExe);
    }

    @Override
    protected Compiler<StaticLibraryArchiverSpec> createStaticLibraryArchiver() {
        GccCommandLineToolConfigurationInternal staticLibArchiverTool = toolRegistry.getTool(ToolType.STATIC_LIB_ARCHIVER);
        ArStaticLibraryArchiver archiver = new ArStaticLibraryArchiver(context(staticLibArchiverTool));
        File archiverExe = commandLineTool(staticLibArchiverTool);
        return compilerFactory.compiler(archiver, staticLibArchiverTool.getToolType().getToolName(), archiverExe);
    }

    private File commandLineTool(GccCommandLineToolConfigurationInternal tool) {
        ToolType key = tool.getToolType();
        String exeName = tool.getExecutable();
        return toolSearchPath.locate(key, exeName).getTool();
    }

    private CommandLineToolContext context(GccCommandLineToolConfigurationInternal toolConfiguration) {
        MutableCommandLineToolContext baseInvocation = new DefaultMutableCommandLineToolContext();
        // MinGW requires the path to be set
        baseInvocation.addPath(toolSearchPath.getPath());
        baseInvocation.addEnvironmentVar("CYGWIN", "nodosfilewarning");
        baseInvocation.setArgAction(toolConfiguration.getArgAction());
        return baseInvocation;
    }

    public String getPCHFileExtension() {
        return ".h.gch";
    }
}
