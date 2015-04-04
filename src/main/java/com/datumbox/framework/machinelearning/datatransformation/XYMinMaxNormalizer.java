/**
 * Copyright (C) 2013-2015 Vasilis Vryniotis <bbriniotis at datumbox.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.datumbox.framework.machinelearning.datatransformation;

import com.datumbox.common.dataobjects.Dataset;
import com.datumbox.common.persistentstorage.interfaces.DatabaseConfiguration;
import com.datumbox.framework.machinelearning.common.bases.datatransformation.BaseDummyMinMaxTransformer;
import java.util.Map;

/**
 * Dummy XY MinMax Normalizer: Transforms the X vector and Y values of the 
 * Records to the 0.0-1.0 scale and builds dummy variables when non-numerics are
 * detected. This normalizer can be used in continuous regression models.
 * 
 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
 */
public class XYMinMaxNormalizer extends BaseDummyMinMaxTransformer {
    
    public XYMinMaxNormalizer(String dbName, DatabaseConfiguration dbConf) {
        super(dbName, dbConf);
    }
    
    @Override
    protected void _transform(Dataset data) {       
        Map<Object, Double> minColumnValues = knowledgeBase.getModelParameters().getMinColumnValues();
        Map<Object, Double> maxColumnValues = knowledgeBase.getModelParameters().getMaxColumnValues();
        
        if(minColumnValues.isEmpty() || maxColumnValues.isEmpty()) {
            //Training Mode
            BaseDummyMinMaxTransformer.fitX(data, minColumnValues, maxColumnValues);
            BaseDummyMinMaxTransformer.fitY(data, minColumnValues, maxColumnValues);
        }
    }
    
    @Override
    protected void _normalize(Dataset data) {
        Map<Object, Double> minColumnValues = knowledgeBase.getModelParameters().getMinColumnValues();
        Map<Object, Double> maxColumnValues = knowledgeBase.getModelParameters().getMaxColumnValues();

        BaseDummyMinMaxTransformer.normalizeX(data, minColumnValues, maxColumnValues);
        BaseDummyMinMaxTransformer.normalizeY(data, minColumnValues, maxColumnValues);
    }
    
    @Override
    protected void _denormalize(Dataset data) {
        Map<Object, Double> minColumnValues = knowledgeBase.getModelParameters().getMinColumnValues();
        Map<Object, Double> maxColumnValues = knowledgeBase.getModelParameters().getMaxColumnValues();

        BaseDummyMinMaxTransformer.denormalizeX(data, minColumnValues, maxColumnValues);
        BaseDummyMinMaxTransformer.denormalizeY(data, minColumnValues, maxColumnValues);
    }

}
