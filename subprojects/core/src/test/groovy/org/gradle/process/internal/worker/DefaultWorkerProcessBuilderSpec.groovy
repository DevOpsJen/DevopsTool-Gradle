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
package org.gradle.process.internal.worker

import org.gradle.internal.id.IdGenerator
import org.gradle.internal.logging.events.OutputEventListener
import org.gradle.internal.remote.MessagingServer
import org.gradle.process.internal.JavaExecHandleFactory
import org.gradle.process.internal.health.memory.MemoryManager
import org.gradle.process.internal.worker.child.ApplicationClassesInSystemClassLoaderWorkerImplementationFactory
import org.gradle.process.internal.JavaExecHandleBuilder
import spock.lang.Specification

import static org.junit.Assert.assertTrue

class DefaultWorkerProcessBuilderSpec extends Specification {
    def javaExecHandleBuilder = Mock(JavaExecHandleBuilder)
    def javaExecHandleFactory = new JavaExecHandleFactory() {
        @Override
        JavaExecHandleBuilder newJavaExec() {
            return javaExecHandleBuilder
        }
    }
    def messagingServer = Mock(MessagingServer)
    def idGenerator = Mock(IdGenerator)
    def applicationClassesInSystemClassLoaderWorkerImplementationFactory = Mock(ApplicationClassesInSystemClassLoaderWorkerImplementationFactory)
    def outputEventListener = Mock(OutputEventListener)
    def memoryManager = Mock(MemoryManager)
    DefaultWorkerProcessBuilder builder = new DefaultWorkerProcessBuilder(javaExecHandleFactory,
        messagingServer,
        idGenerator,
        applicationClassesInSystemClassLoaderWorkerImplementationFactory,
        outputEventListener,
        memoryManager)


    def "validate entries in classpath"() {
        when:
        List<File> paths = new ArrayList()

        String validPath1 = System.getProperty("user.dir")
        String validPath2 = System.getProperty("java.home")
        String validPath3 = System.getProperty("java.io.tmpdir")
        String validPath4 = System.getProperty("user.home")
        String validPath5 = System.getProperty("user.home") + File.separator + "*"
        String validPath6 = "/*"
        String inValidPath1 = System.getProperty("user.home") + File.separator + "Non exist path"
        String inValidPath2 = System.getProperty("user.home") + File.separator + "Non exist path" + File.separator + "*"

        paths.add(new File(validPath1))
        paths.add(new File(inValidPath2))
        paths.add(new File(validPath2))
        paths.add(new File(validPath3))
        paths.add(new File(inValidPath1))
        paths.add(new File(validPath4))
        paths.add(new File(validPath5))
        paths.add(new File(validPath6))

        def validPathSet = builder.applicationClasspath(paths).getApplicationClasspath()

        then:
        assertTrue(6 == validPathSet.size())
    }
}
