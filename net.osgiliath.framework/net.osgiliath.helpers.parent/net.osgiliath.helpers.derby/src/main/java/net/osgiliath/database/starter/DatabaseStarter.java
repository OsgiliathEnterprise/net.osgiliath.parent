package net.osgiliath.database.starter;

/*
 * #%L
 * Osgiliath integration tests JPA database
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath
 * %%
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
 * #L%
 */

import java.io.PrintWriter;

import lombok.Setter;
import lombok.extern.slf4j.*;
import org.apache.derby.drda.NetworkServerControl;
@Slf4j
public class DatabaseStarter {
    @Setter
    private NetworkServerControl control;
    @Setter
    private PrintWriter writer;

    public void init() throws Exception {
	log.info("Starting Derby datasource");
	control.start(writer);
    }
}
