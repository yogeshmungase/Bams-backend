package com.management.studentattendancesystem.base.imgenhancer;

import com.management.studentattendancesystem.base.db.model.Student;
import com.management.studentattendancesystem.base.rest.mapper.StudentThumbDetails;
import com.management.studentattendancesystem.base.rest.mapper.StudentThumbProperties;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageProcessingExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ImageProcessingExecutor.class);

    public static StudentThumbDetails processData(List<Student> studentList, String imageType) {
        logger.info("Starting image processing data");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        StudentThumbDetails studentThumbDetails = new StudentThumbDetails();
        List<StudentThumbProperties> studentThumbPropertiesList = new ArrayList<>();

        logger.info("Creating thread pool of size : {}", studentList.size());
        ExecutorService executor = Executors.newFixedThreadPool(studentList.size());
        List<Future<StudentThumbProperties>> futures = new ArrayList<>();

        for (Student student : studentList) {
            ImageProcessing.ImageProcessingTask imageProcessingTask = new ImageProcessing.ImageProcessingTask(student, imageType);
            Future<StudentThumbProperties> future = executor.submit(imageProcessingTask);
            futures.add(future);
        }
        executor.shutdown();
        try {
            for (Future<StudentThumbProperties> future : futures) {
                StudentThumbProperties thumbProperties = future.get(); // Retrieve the metadata
                studentThumbPropertiesList.add(thumbProperties);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        studentThumbDetails.setThumbPropertiesList(studentThumbPropertiesList);
        stopWatch.stop();
        logger.info("Total time taken to process data is : {}", stopWatch.getTotalTimeSeconds());

        return studentThumbDetails;
    }
}
