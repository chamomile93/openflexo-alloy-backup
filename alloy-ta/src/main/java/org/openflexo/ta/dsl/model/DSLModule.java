package org.openflexo.ta.dsl.model;

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.ta.dsl.rm.DSLResource;

import java.util.logging.Logger;

public interface DSLModule extends DSLObject, ResourceData<DSLModule> {


    @Override
    public DSLResource getResource();

    /**
     * Retrieve object with supplied serialization identifier, asserting this object resides in this {@link DSLModule}
     *
     * @param objectId
     * @return
     */
    public DSLObject getObjectWithSerializationIdentifier(String objectId);

    /**
     * Default base implementation for {@link DSLModule}
     *
     * @author sylvain
     *
     */
    public static abstract class DSLModuleImpl extends DSLObjectImpl implements DSLModule {

        @SuppressWarnings("unused")
        private static final Logger logger = Logger.getLogger(DSLModuleImpl.class.getPackage().getName());

        @Override
        public DSLModule getResourceData() {
            return this;
        }

        @Override
        public DSLResource getResource() {
            return (DSLResource) performSuperGetter(FLEXO_RESOURCE);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("[Module]\n"); //TODO

            return sb.toString();
        }

    }
}