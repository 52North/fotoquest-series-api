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
package org.n52.io.response.v2.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import org.n52.io.response.OutputCollection;
import org.n52.io.response.ParameterOutput;
import org.n52.io.response.v1.FeatureOutput;
import org.n52.io.response.v1.PhenomenonOutput;
import org.n52.io.response.v1.ProcedureOutput;

/**
 * TODO: JavaDoc
 *
 * @author <a href="mailto:h.bredel@52north.org">Henning Bredel</a>
 */
public class PlatformOutput extends ParameterOutput implements PlatformItemOutput {

    private final PlatformType platformType;

    private SeriesOutputCollection series;

    private GeometryOutputCollection geometries;

    private PhenomenonOutputCollection phenomena;

    private ProcedureOutputCollection procedures;

    private FeatureOutputCollection features;

    public PlatformOutput(PlatformType platformType) {
        this.platformType = platformType;
    }

    @Override
    public String getPlatformType() {
        return platformType.getTypeName();
    }

    @JsonIgnore
    public PlatformType getType() {
        return platformType;
    }

    @Override
    public void setId(String id) {
        super.setId(getPlatformType() + "/" + id);
    }

    public Collection<SeriesMetadataOutput<SeriesParameters>> getSeries() {
        return getNullSafeItems(series);
    }

    public void setSeries(SeriesOutputCollection series) {
        this.series = series;
    }

    public Collection<GeometryInfo> getGeometries() {
        return getNullSafeItems(geometries);
    }

    public void setGeometries(GeometryOutputCollection geometries) {
        this.geometries = geometries;
    }

    public Collection<PhenomenonOutput> getPhenomena() {
        return getNullSafeItems(phenomena);
    }

    public void setPhenomena(PhenomenonOutputCollection phenomena) {
        this.phenomena = phenomena;
    }

    public Collection<ProcedureOutput> getProcedures() {
        return getNullSafeItems(procedures);
    }

    public void setProcedures(ProcedureOutputCollection procedures) {
        this.procedures = procedures;
    }

    public Collection<FeatureOutput> getFeatures() {
        return getNullSafeItems(features);
    }

    public void setFeatures(FeatureOutputCollection features) {
        this.features = features;
    }

    private <T> Collection<T> getNullSafeItems(OutputCollection<T> collection) {
        return collection != null
                ? collection.getItems()
                : null;
    }

}
