/*
 * Copyright (C) 2013-2016 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public License
 * version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package org.n52.series.db.da;

import java.util.List;

import org.hibernate.Session;
import org.n52.io.response.dataset.AbstractValue;
import org.n52.io.response.dataset.Data;
import org.n52.io.response.v1.ext.DatasetType;
import org.n52.series.db.DataAccessException;
import org.n52.series.db.SessionAwareRepository;
import org.n52.series.db.beans.DataEntity;
import org.n52.series.db.beans.DataParameter;
import org.n52.series.db.beans.DatasetEntity;
import org.n52.series.db.beans.GeometryEntity;
import org.n52.series.db.dao.DbQuery;
import org.n52.series.db.dao.SeriesDao;

public abstract class AbstractDataRepository<T extends Data, E extends DatasetEntity>
        extends SessionAwareRepository<DbQuery> implements DataRepository<T, E> {

    @Override
    public T getData(String seriesId, DbQuery dbQuery) throws DataAccessException {
        Session session = getSession();
        try {
            SeriesDao<E> seriesDao = new SeriesDao<>(session, getEntityType());
            String id = DatasetType.extractId(seriesId);
            E series = seriesDao.getInstance(parseId(id), dbQuery);
            return dbQuery.isExpanded()
                ? assembleDataWithReferenceValues(series, dbQuery, session)
                : assembleData(series, dbQuery, session);
        }
        finally {
            returnSession(session);
        }
    }

    protected abstract T assembleData(E seriesEntity, DbQuery query, Session session) throws DataAccessException;

    protected abstract T assembleDataWithReferenceValues(E timeseries, DbQuery dbQuery, Session session) throws DataAccessException;

    protected boolean hasValidEntriesWithinRequestedTimespan(List<?> observations) {
        return observations.size() > 0;
    }

    protected boolean hasSingleValidReferenceValue(List<?> observations) {
        return observations.size() == 1;
    }

    protected void addGeometry(DataEntity<?> observation, AbstractValue<?> value) {
        if (observation.isSetGeometry()) {
            GeometryEntity geometry = observation.getGeometry();
            value.setGeometry(geometry.getGeometry(getDatabaseSrid()));
        }
    }

    protected void addValidTime(DataEntity<?> observation, AbstractValue<?> value) {
        // TODO add validTime to value
        if (observation.isSetValidStartTime()) {
            observation.getValidTimeStart().getTime();
        }
        if (observation.isSetValidEndTime()) {
            observation.getValidTimeEnd().getTime();
        }
    }

    protected void addParameter(DataEntity<?> observation, AbstractValue<?> value) {
        if (observation.hasParameters()) {
            for (DataParameter<?> parameter : observation.getParameters()) {
                // TODO add parameters to value
                parameter.getName();
                parameter.getValue();
            }
        }
    }

}
