package com.example.ticketsbooking.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ticketsbooking.models.Shows;
import com.example.ticketsbooking.models.ShowsHistory;
import com.example.ticketsbooking.repository.Showshisrepo;
import com.example.ticketsbooking.repository.Showsrepo;



@Service
public class Showstatusscheduler {

    private final Showshisrepo showshisrepo;
    private final Showsrepo showsrepo;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final ConcurrentHashMap<String, ScheduledFuture<?>> schuduledTaks = new ConcurrentHashMap<>();

    @Autowired
    public Showstatusscheduler(Showshisrepo showshisrepo, Showsrepo showsrepo) {
        this.showshisrepo = showshisrepo;
        this.showsrepo = showsrepo;
    }

    public void scheduleShowTasks(Shows existShow) {
        

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now().withNano(0);

        LocalTime showStartTime = LocalTime.parse(existShow.getShowstarttime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime showEndTime = LocalTime.parse(existShow.getShowendtime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalDate showDate = LocalDate.parse(existShow.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (currentDate.equals(showDate)) {
            long delayForShowStart = currentTime.until(showStartTime, TimeUnit.MINUTES.toChronoUnit());
            long delayFor30MinutesBeforeStart = currentTime.until(showStartTime.minusMinutes(25), TimeUnit.MINUTES.toChronoUnit());
            long delayForShowEnd = currentTime.until(showEndTime, TimeUnit.MINUTES.toChronoUnit());

            System.out.println(delayFor30MinutesBeforeStart);
            System.out.println(delayForShowStart);
System.out.println(delayForShowEnd);



            
            if (delayFor30MinutesBeforeStart > 0) {
            	scheduler.schedule(() -> {
                    try {
                        System.err.println("Modifying booking status...");
                        existShow.setBookingstatus(false);
                        showsrepo.save(existShow);
                        System.out.println("Booking status saved successfully.");
                    } catch (Exception e) {
                        System.err.println("Error modifying booking status: " + e.getMessage());
                    }
                }, delayFor30MinutesBeforeStart, TimeUnit.MINUTES);
                
            }

            
            ShowsHistory existshowhistoru = showshisrepo.findByShows(existShow);

            // Schedule task for marking the show as ON-GOING at show start time
            if (delayForShowStart > 0) {
            	  scheduler.schedule(() -> {
                    try {
                        System.out.println("Marking the show as ON-GOING...");
                        if (existshowhistoru != null) {
                            existshowhistoru.setShowstatus("ON-GOING");
                            showshisrepo.save(existshowhistoru);
                            System.out.println("Show marked as ON-GOING.");
                        } else {
                            System.err.println("Show history not found for ON-GOING status.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error marking show as ON-GOING: " + e.getMessage());
                    }
                }, delayForShowStart, TimeUnit.MINUTES);
            	
            }

       
            if (delayForShowEnd > 0) {
               scheduler.schedule(() -> {
                    try {
                        System.out.println("Marking the show as COMPLETED...");
                        if (existshowhistoru != null) {
                            existshowhistoru.setShowstatus("COMPLETED");
                            showshisrepo.save(existshowhistoru);
                            System.out.println("Show marked as COMPLETED.");
                        } else {
                            System.err.println("Show history not found for COMPLETED status.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error marking show as COMPLETED: " + e.getMessage());
                    }
                }, delayForShowEnd, TimeUnit.MINUTES);

            }
        }
    }

    
    
    public void removeShowTasks(int id) {
    	
        ScheduledFuture<?> bookingTask = schuduledTaks.remove(id+ "BookingStatus");
        if(bookingTask != null) {
        	bookingTask.cancel(false);System.out.println("Booking status task canceled for show: " + id);
        }
        ScheduledFuture<?> onGoingTask = schuduledTaks.remove(id+ "onGoing");
        if (onGoingTask != null) {
            onGoingTask.cancel(false);
            System.out.println("On-going status task canceled for show: " + id);
        }
        ScheduledFuture<?> completedTask = schuduledTaks.remove(id+ "Completed");
        if (completedTask != null) {
            completedTask.cancel(false);
            System.out.println("Completed status task canceled for show: " + id);
        }
        
    	
    }
}
