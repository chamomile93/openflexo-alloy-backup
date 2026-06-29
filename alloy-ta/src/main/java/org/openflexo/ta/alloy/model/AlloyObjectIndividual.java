/**
 *
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012, THALES SYSTEMES AEROPORTES - All Rights Reserved
 * Copyright (c) 2012-2012, AgileBirds
 * <p>
 * This file is part of Alloyconnector, a component of the software
 * infrastructure
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

/** Copyright (c) 2012, THALES SYSTEMES AEROPORTES - All Rights Reserved
 * Author : Gilles Besançon
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7
 *
 * If you modify this Program, or any covered work, by linking or 
 * combining it with eclipse Alloy (or a modified version of that library),
 * containing parts covered by the terms of EPL 1.0, the licensors of this 
 * Program grant you additional permission to convey the resulting work.
 *
 * Contributors :
 *
 */

package org.openflexo.ta.alloy.model;

import edu.mit.csail.sdg.parser.CompModule;
import org.openflexo.foundation.InnerResourceData;
import org.openflexo.foundation.ontology.*;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;

import java.util.List;
import java.util.logging.Logger;

/**
 * Supposed to be an Alloy Object Individual.
 *
 * <T> is supposed to be the super type of any org.alloytools concepts
 * @author chamomile93
 */
public class AlloyObjectIndividual extends FlexoOntologyObjectImpl<AlloyTechnologyAdapter> implements InnerResourceData<AlloyModel>, IFlexoOntologyIndividual<AlloyTechnologyAdapter> {

    private static final Logger logger = Logger.getLogger(AlloyObjectIndividual.class.getPackage().getName());

    protected final AlloyModel ontology;
    /** Alloy Object Wrapped. */
    protected final CompModule object;

    public AlloyObjectIndividual(AlloyModel ontology, CompModule object) {
        this.ontology = ontology;
        this.object = object;
    }

    /**
     * Annotation upon Concept.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyAnnotation> getAnnotations() {
        return List.of();
    }

    /**
     * Container of Concept.
     *
     * @return
     */
    @Override
    public IFlexoOntologyConceptContainer<AlloyTechnologyAdapter> getContainer() {
        return null;
    }

    /**
     * Association with structural features for Concept.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyFeatureAssociation<AlloyTechnologyAdapter>> getStructuralFeatureAssociations() {
        return List.of();
    }

    /**
     * Association with behavioural features for Concept.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyFeatureAssociation<AlloyTechnologyAdapter>> getBehaviouralFeatureAssociations() {
        return List.of();
    }

    /**
     *
     * Is this a Super Concept of concept.
     *
     * @param concept
     * @return
     */
    @Override
    public boolean isSuperConceptOf(IFlexoOntologyConcept<AlloyTechnologyAdapter> concept) {
        return false;
    }

    /**
     *
     * Is this a Sub Concept of concept.
     *
     * @param concept
     * @return
     */
    @Override
    public boolean isSubConceptOf(IFlexoOntologyConcept<AlloyTechnologyAdapter> concept) {
        return false;
    }

    /**
     * Follow the link.
     *
     * @see IFlexoOntologyConcept#accept(IFlexoOntologyConceptVisitor)
     */
    @Override
    public <T> T accept(IFlexoOntologyConceptVisitor<T> visitor) {
        return visitor.visit(this);
    }

    /**
     * This equals has a particular semantics (differs from {@link #equals(Object)} method) in the way that it returns true only and only if
     * compared objects are representing same concept regarding URI. This does not guarantee that both objects will respond the same way to
     * some methods.<br>
     * This method returns true if and only if objects are same, or if one of both object redefine the other one (with eventual many levels)
     *
     * @param concept@return
     */
    @Override
    public boolean equalsToConcept(IFlexoOntologyConcept<AlloyTechnologyAdapter> concept) {
        return false;
    }

    /**
     * Return types of Individual.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyClass<AlloyTechnologyAdapter>> getTypes() {
        return List.of();
    }

    /**
     * Follow the link.
     *
     * @see IFlexoOntologyIndividual#isIndividualOf(IFlexoOntologyClass)
     */
    @Override
    public boolean isIndividualOf(IFlexoOntologyClass<AlloyTechnologyAdapter> aClass) {
        return getTypes().contains(aClass);
    }

    /**
     * Property Values.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyPropertyValue<AlloyTechnologyAdapter>> getPropertyValues() {
        return List.of();
    }

    /**
     * Follow the link.
     *
     * @see org.openflexo.foundation.ontology.IFlexoOntologyObject#getTechnologyAdapter()
     */
    @Override
    public AlloyTechnologyAdapter getTechnologyAdapter() {
        return ontology.getTechnologyAdapter();
    }

    /**
     * Follow the link.
     *
     * @see IFlexoOntologyIndividual#getPropertyValue(IFlexoOntologyStructuralProperty)
     */
    @Override
    public IFlexoOntologyPropertyValue<AlloyTechnologyAdapter> getPropertyValue(IFlexoOntologyStructuralProperty<AlloyTechnologyAdapter> property) {
        IFlexoOntologyPropertyValue<AlloyTechnologyAdapter> result = null;
        for (IFlexoOntologyPropertyValue<AlloyTechnologyAdapter> propertyValue : getPropertyValues()) {
            if (result == null && property.equalsToConcept(propertyValue.getProperty())) {
                result = propertyValue;
            }
        }
        return result;
    }

    /**
     * Add newValue as a value for supplied property<br>
     * Return the {@link IFlexoOntologyPropertyValue} matching supplied property and defined for this individual<br>
     *
     * @param property
     * @param newValue
     * @return
     */
    @Override
    public IFlexoOntologyPropertyValue<AlloyTechnologyAdapter> addToPropertyValue(IFlexoOntologyStructuralProperty<AlloyTechnologyAdapter> property, Object newValue) {
        return null;
    }

    /**
     * Follow the link.
     *
     * @see IFlexoOntologyIndividual#removeFromPropertyValue(IFlexoOntologyStructuralProperty,
     *      Object)
     */
    @Override
    public IFlexoOntologyPropertyValue<AlloyTechnologyAdapter> removeFromPropertyValue(IFlexoOntologyStructuralProperty<AlloyTechnologyAdapter> property, Object valueToRemove) {
        System.out.println("Property Values can't be modified.");
        return null;
    }

    @Override
    public String toString() {
        // return "AlloyObjectIndividual/" + getTypes().get(0) + ":" +
        // getName()
        // + "uri=" + getURI();
        return getTypes().get(0).getName() + ":" + getName();
    }

    @Override
    public AlloyModel getResourceData() {
        return null;
    }

    /**
     * Name of Object.
     *
     * @return
     */
    @Override
    public String getName() {
        return "";
    }

    /**
     * Sets name of object
     *
     * @param name
     * @throws Exception
     */
    @Override
    public void setName(String name) throws Exception {

    }

    /**
     * Uri of Object.
     *
     * @return
     */
    @Override
    public String getURI() {
        return "";
    }

    /**
     * Description of Object.
     *
     * @return
     */
    @Override
    public String getDescription() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public IFlexoOntology<AlloyTechnologyAdapter> getFlexoOntology() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getDisplayableDescription() {
        return "";
    }
}