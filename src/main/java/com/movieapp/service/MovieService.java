package com.movieapp.service;

import com.movieapp.aggregate.MovieInfo;
import com.movieapp.aggregate.MovieSchedule;
import com.movieapp.aggregate.Theater;
import com.movieapp.repository.MovieRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private final MovieRepository mr = new MovieRepository();

    public MovieService() {
        System.out.println("MovieService 생성");
    }

    // 영화 상영 스케줄 표 보기
    public void showMovieSchedule(int[] scheduleFilter) {
        int firstFilter = scheduleFilter[0];
        int secondFilter = scheduleFilter[1];

        List<MovieSchedule> filteredSchedules = new ArrayList<>();

        switch (firstFilter) {
            case 1: // 전체 상영 스케줄표 조회
                System.out.println("======= 전체 상영 스케줄 표 =======");
                showAllSchedule();
                break;
            case 2: // 극장 별로 조회
                System.out.println("======= 극장별 스케줄 표 =======");
                Theater theater = null;
                Theater[] theaters = Theater.values();
                theater = theaters[secondFilter - 1];   // 극장 이름 받음.
                filteredSchedules = mr.selectTheaterSchedule(theater);
                break;
            case 3:     // 영화 별로 조회
                System.out.println("======= 영화별 스케줄 표 =======");
                filteredSchedules = mr.selectedMovieInfoSchedule(secondFilter);
                break;
            case 4:     // 날짜별로 조회 (ex. 2025-02-16)
                System.out.println("======= 날짜별 스케줄 표 =======");

                int days = secondFilter + 11; // 12일 ~ 19일
                System.out.println(days);
                LocalDate selectedDate = LocalDate.of(2025, 2, days);
                filteredSchedules = mr.selectedDateSchedule(selectedDate);
                break;

            default:
                System.out.println("번호를 잘못 입력하셨습니다.");
        }
        for(MovieSchedule movieSchedule : filteredSchedules) {
            System.out.println(movieSchedule.getDate() + " " + movieSchedule.getScheduleNo() + ". " + movieSchedule.getMovieInfo().getMovieName()
                    + " " + movieSchedule.getTheaterName() + " " + movieSchedule.getEmptySeats());
        }
        System.out.println();
        System.out.println("\uD83D\uDD19 메인으로 돌아가기");

    }

    // 전체 영화 목록 조회
    public  void showAllMovies() {
        List<MovieInfo> allMovies = mr.selectAllMovies();

        System.out.println(allMovies.toString());
        for(MovieInfo movieInfo : allMovies) {
            System.out.println(movieInfo.getMovieNo() + ". " + movieInfo.getMovieName());
        }
    }


    // 전체 상영 스케줄표를 조회
    public void showAllSchedule() {
        List<MovieSchedule> allSchedules = mr.selectAllSchedules();

        for(MovieSchedule movieSchedule : allSchedules) {
            System.out.println(movieSchedule.getScheduleNo() + ". " + movieSchedule.getDate() + " " +  movieSchedule.getMovieInfo().getMovieName()
                    + " " + movieSchedule.getTheaterName() + " " + movieSchedule.getEmptySeats());
        }
    }
    // 전체 극장 목록을 조회
    public void showAllTheaters() {
//        List<String> theaterList = new ArrayList<>();

        for(Theater theater : Theater.values()) {
            System.out.println((theater.ordinal() + 1) + ". " + theater.getTheater());
        }

    }


    // 티켓 예매
    public void reservTicket(Object input) {
        if(input == null){
            return;
        }

        //
    }

    // 특정 영화를 상영하는 극장 내역을 보여주는 메서드 .
    public List<Theater> showExistTheater(int movieNumber) {
        List<MovieSchedule> allSchedules = mr.selectAllSchedules();
        List<Theater> existTheaters = new ArrayList<>();

        for(MovieSchedule movieSchedule : allSchedules) {
            if(movieSchedule.getMovieInfo().getMovieNo() == (movieNumber)) {
                if(!existTheaters.contains(movieSchedule.getTheaterName())){
                    existTheaters.add(movieSchedule.getTheaterName());
                }
            }
        }
        return existTheaters;
//        int theaterCount = 0;
//        for(Theater theater : existTheaters) {
//            System.out.println(theaterCount++ + theater.getTheater());
//        }
    }

    public List<LocalDate> showExistDate(int movieNumber, Theater inputTheater) {
        List<MovieSchedule> allSchedules = mr.selectAllSchedules();
        List<LocalDate> existDates = new ArrayList<>();

        for(MovieSchedule movieSchedule : allSchedules) {
            if(movieSchedule.getMovieInfo().getMovieNo() == (movieNumber) && movieSchedule.getTheaterName().equals(inputTheater)) {
                if (!existDates.contains(movieSchedule.getDate())) {
                    existDates.add(movieSchedule.getDate());
                }
            }
        }
        return existDates;
    }
}