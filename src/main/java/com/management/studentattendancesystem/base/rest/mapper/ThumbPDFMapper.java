package com.management.studentattendancesystem.base.rest.mapper;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.List;

public class ThumbPDFMapper {

    private static final Logger logger = LoggerFactory.getLogger(ThumbPDFMapper.class);

    public static VelocityContext mapStudentThumbDetails(StudentThumbDetails studentThumbDetails) throws ParseException {

        VelocityContext context = new VelocityContext();

        if (!CollectionUtils.isEmpty(studentThumbDetails.getThumbPropertiesList())) {
            context.put("studentThumbDetails", studentThumbDetails.getThumbPropertiesList());
        }

        return context;
    }
}
