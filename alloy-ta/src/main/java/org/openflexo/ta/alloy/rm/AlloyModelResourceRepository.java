/**
 *
 * Copyright (c) 2018, Openflexo
 * <p>
 * This file is part of OpenflexoTechnologyAdapter, a component of the software infrastructure
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

package org.openflexo.ta.alloy.rm;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.technologyadapter.ModelRepository;
import org.openflexo.pamela.annotations.ModelEntity;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.pamela.factory.PamelaModelFactory;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;
import org.openflexo.ta.alloy.metamodel.AlloyMetaModel;
import org.openflexo.ta.alloy.model.AlloyModel;

/**
 * Supposed to be an Alloy Resource repository
 *
 * A repository which references to some {@link AlloyModelResource}
 *
 * @author chamomile93
 *
 */
@ModelEntity
public interface AlloyModelResourceRepository<I> extends ModelRepository<AlloyModelResource, AlloyModel, AlloyMetaModel, AlloyTechnologyAdapter, AlloyTechnologyAdapter, I> {

    static <I> AlloyModelResourceRepository<I> instanciateNewRepository(AlloyTechnologyAdapter technologyAdapter, FlexoResourceCenter<I> resourceCenter) {
        try {
            PamelaModelFactory factory = new PamelaModelFactory(AlloyModelResourceRepository.class);
            AlloyModelResourceRepository<I> newRepository = factory.newInstance(AlloyModelResourceRepository.class);
            newRepository.setTechnologyAdapter(technologyAdapter);
            newRepository.setResourceCenter(resourceCenter);
            newRepository.setBaseArtefact(resourceCenter.getBaseArtefact());
            newRepository.getRootFolder().setRepositoryContext(null);
            return newRepository;
        } catch (ModelDefinitionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
