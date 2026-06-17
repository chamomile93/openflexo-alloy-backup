/**
 * 
 * Copyright (c) 2018, Openflexo
 * 
 * This file is part of OpenflexoTechnologyAdapter, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.ta.alloy;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.test.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.ta.alloy.rm.AlloyModelResourceFactory;
import org.openflexo.toolbox.FileUtils;

/**
 * @author chamomile93
 */
public abstract class AbstractAlloyTest extends OpenflexoProjectAtRunTimeTestCase {
	protected static final Logger logger = Logger.getLogger(AbstractAlloyTest.class.getPackage().getName());

	protected void copyAlloySourceFiles(String relativePath) {
		Resource targetResource = ResourceLocator.locateResource(relativePath);
		System.out.println("targetResource=" + targetResource + " of " + targetResource.getClass());

		Resource sourceResource = ResourceLocator.locateSourceCodeResource(relativePath);
		System.out.println("sourceResource=" + sourceResource + " of " + sourceResource.getClass());

		// Skipping since targetResource is JarResource and sourceResource is
		// FileResource
		if (targetResource instanceof FileResourceImpl && sourceResource instanceof FileResourceImpl) {
			File srcDir = ((FileResourceImpl) sourceResource).getFile();
			File dstDir = ((FileResourceImpl) targetResource).getFile();
			try {
				FileUtils.copyContentDirToDir(srcDir, dstDir, FileUtils.CopyStrategy.REPLACE, new FileFilter() {
					@Override
					public boolean accept(File path) {
						if (path.isDirectory()) {
							return true;
						}
						return path.getName().endsWith(AlloyModelResourceFactory.ALS_FILE_EXTENSION);
					}
				});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected <R extends FlexoResource<?>> R getResourceWithSerializationArtefactWithName(Collection<R> resources,
			String resourceName) {
		for (R resource : resources) {
			log("iter resource =" + resource.getName());
			log("getting resourceName= " + resourceName);
			if (resource.getIODelegate().getSerializationArtefactName().contains(resourceName)) {
				log("is eql rtn "+resourceName);
				return resource;
			}
		}
		log("not eql rtn null");
		return null;
	}
}
