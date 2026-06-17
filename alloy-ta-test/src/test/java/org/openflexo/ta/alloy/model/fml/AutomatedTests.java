/**
 *
 * Copyright (c) 2014, Openflexo
 * <p>
 * This file is part of Cartoeditor, a component of the software infrastructure
 * developed at Openflexo.
 * <p>
 * <p>
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either
 * version 1.1 of the License, or any later version ), which is available at
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * <p>
 * You can redistribute it and/or modify under the terms of either of these licenses
 * <p>
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 * <p>
 * Additional permission under GNU GPL version 3 section 7
 * <p>
 * If you modify this Program, or any covered work, by linking or
 * combining it with software containing parts covered by the terms
 * of EPL 1.0, the licensors of this Program grant you additional permission
 * to convey the resulting work. *
 * <p>
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p>
 * See http://www.openflexo.org/license.html for details.
 * <p>
 * <p>
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 *
 */

package org.openflexo.ta.alloy.model.fml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openflexo.foundation.DefaultFlexoEditor;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.fml.cli.CommandInterpreter;
import org.openflexo.foundation.fml.cli.ParseException;
import org.openflexo.foundation.fml.cli.command.FMLCommandExecutionException;
import org.openflexo.foundation.fml.cli.command.FMLScript;
import org.openflexo.foundation.fml.cli.test.FMLScriptParserTestCase;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.rm.Resources;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

/**
 * A parameterized suite of unit tests iterating on FML script files.
 * <p>
 * For each FML-script file, execute it. All asserts are executed and must success
 *
 */
@RunWith(Parameterized.class)
public class AutomatedTests extends FMLScriptParserTestCase {

    private final Resource fmlResource;
    private FlexoEditor editor;
    private FMLScript script;
    private CommandInterpreter commandInterpreter;

    public AutomatedTests(Resource fmlResource, String name) throws ParseException, ModelDefinitionException, IOException {
        log("********* Launch FML-script " + fmlResource + " name=" + name);
        this.fmlResource = fmlResource;
        initServiceManager();
    }

    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object[]> generateData() {
        return Resources.getMatchingResource(ResourceLocator.locateResource("TestResourceCenter/AutomatedTests"), ".fmlscript");
    }

    //TODO this does not fail if fmlscript is malformed, for instance if a
    // ModelSlot is not defined, it still pass
    @Test
    public void checkScript() throws ModelDefinitionException, ParseException, IOException, FMLCommandExecutionException {
        log("Parse script " + fmlResource.getRelativePath());
        script = parseFMLScript(fmlResource, commandInterpreter);
        checkFMLScript(fmlResource.getRelativePath(), script);
        script.execute();
    }

    public void initServiceManager() throws ParseException, ModelDefinitionException, IOException {
        instanciateTestServiceManager(AlloyTechnologyAdapter.class);

        editor = new DefaultFlexoEditor(null, serviceManager);
        assertNotNull(editor);

        commandInterpreter = new CommandInterpreter(serviceManager, System.in, System.out, System.err, HOME_DIR);
    }
}
