/**
 * ﻿Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * This program is free software; you can redistribute and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */

package org.n52.web.v1.ctrl;

import static org.n52.io.QueryParameters.createFromQuery;
import static org.n52.web.v1.ctrl.RestfulUrls.COLLECTION_TIMESERIES;
import static org.n52.web.v1.ctrl.RestfulUrls.DEFAULT_PATH;
import static org.n52.web.v1.ctrl.Stopwatch.startStopwatch;

import org.n52.io.IoParameters;
import org.n52.io.v1.data.TimeseriesMetadataOutput;
import org.n52.web.ResourceNotFoundException;
import org.n52.web.v1.srv.ParameterService;
import org.n52.web.v1.srv.TransformingTimeseriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = DEFAULT_PATH + "/" + COLLECTION_TIMESERIES, produces = {"application/json"})
public class TimeseriesMetadataController extends ParameterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeseriesMetadataController.class);

    private ParameterService<TimeseriesMetadataOutput> timeseriesMetadataService;

    @Override
    public ModelAndView getCollection(@RequestParam(required = false) MultiValueMap<String, String> query) {
        IoParameters map = createFromQuery(query);

        if (map.isExpanded()) {
            Stopwatch stopwatch = startStopwatch();
            Object[] result = timeseriesMetadataService.getExpandedParameters(map);
            LOGGER.debug("Processing request took {} seconds.", stopwatch.stopInSeconds());

            // TODO add paging

            return new ModelAndView().addObject(result);
        }
        else {
            Stopwatch stopwatch = startStopwatch();
            Object[] result = timeseriesMetadataService.getCondensedParameters(map);
            LOGGER.debug("Processing request took {} seconds.", stopwatch.stopInSeconds());

            // TODO add paging

            return new ModelAndView().addObject(result);
        }
    }

    @Override
    public ModelAndView getItem(@PathVariable("item") String timeseriesId,
                                @RequestParam(required = false) MultiValueMap<String, String> query) {
        IoParameters map = createFromQuery(query);

        // TODO check parameters and throw BAD_REQUEST if invalid

        Stopwatch stopwatch = startStopwatch();
        TimeseriesMetadataOutput metadata = timeseriesMetadataService.getParameter(timeseriesId, map);
        LOGGER.debug("Processing request took {} seconds.", stopwatch.stopInSeconds());

        if (metadata == null) {
            throw new ResourceNotFoundException("The timeseries with id '" + timeseriesId + "' was not found.");
        }

        return new ModelAndView().addObject(metadata);
    }

    public ParameterService<TimeseriesMetadataOutput> getTmeseriesMetadataService() {
        return timeseriesMetadataService;
    }

    public void setTimeseriesMetadataService(ParameterService<TimeseriesMetadataOutput> timeseriesMetadataService) {
        this.timeseriesMetadataService = new TransformingTimeseriesService(timeseriesMetadataService);
    }

}
