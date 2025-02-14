package com.movieapp.repository;

import com.movieapp.aggregate.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieRepository {
    /* 설명. 프로그램이 실행될 때 (초기화 - 파일 없을 때)
     *   1. 영화 목록 리스트가 먼저 생성됨
     *   2. 영화 상영 스케줄 파일이 생성됨
     */

    // MovieRepository에서 관리할 "영화리스트", "상영스케줄", "예매내역목록" 컬렉션
    private final List<MovieInfo> movieList = new ArrayList<>();   // 영화 정보 리스트
    private final List<MovieSchedule> movieSchedule = new ArrayList<MovieSchedule>();   // 영화 상영스케줄표 목록
    private final List<Ticket> ticketList = new ArrayList<>();     // 예매 내역 목록

    // 영화 정보 리스트가 저장되어 있는 파일
    private final File movieFile =
            new File("Movie-Application/src/main/java/com/movieapp/db/movieListDB.dat");

    // 영화 상영 스케줄표가 저장되어 있는 파일
    private final File scheduleFile =
            new File("Movie-Application/src/main/java/com/movieapp/db/movieScheduleDB.dat");

    private final File ticketFile =
            new File("Movie-Application/src/main/java/com/movieapp/db/ticketListDB.dat");

    // 기본 생성자
    public MovieRepository() {
        System.out.println("MovieRepository 생성 테스트 ");

        if(!movieFile.exists() && ! scheduleFile.exists()) {
//            System.out.println("파일 생성하러 가기");
            initializeData();
        }

//        System.out.println("파일이 이미 존재합니다. ");

        loadMovies();       // 영화 정보 리스트 읽어오기
        loadSchedules();    // 영화 상영 스케줄표 목록 읽어오기
    }

    private void initializeData() {
//        System.out.println("initializeData");
        // 영화 목록 파일이 존재하지 않으면 생성됨
        // 🎬 영화 정보 리스트 생성
        List<MovieInfo> defaultMovieList = new ArrayList<>();

        defaultMovieList.add(new MovieInfo(1, "캡틴 아메리카: 브레이브 뉴 월드", 9.44, MovieGenre.ACTION, MovieGrade.TWELVE, 118));
        defaultMovieList.add(new MovieInfo(2, "말할 수 없는 비밀", 8.38, MovieGenre.ROMANCE, MovieGrade.ALL, 103));
        defaultMovieList.add(new MovieInfo(3, "히트맨 2", 7.57, MovieGenre.ACTION, MovieGrade.FIFTHTEEN, 118));
        defaultMovieList.add(new MovieInfo(4, "검은 수녀들", 6.53, MovieGenre.MYSTERY, MovieGrade.FIFTHTEEN, 114));
        defaultMovieList.add(new MovieInfo(5, "브로큰", 5.74, MovieGenre.DRAMA, MovieGrade.FIFTHTEEN, 99));
        defaultMovieList.add(new MovieInfo(6, "하얼빈", 8.09, MovieGenre.HISTORY, MovieGrade.FIFTHTEEN, 114));
        defaultMovieList.add(new MovieInfo(7, "쿠로코의 농구 라스트 게임", 9.48, MovieGenre.ANIMATION, MovieGrade.TWELVE, 91));

//        System.out.println("영화 리스트 생성: " + defaultMovieList);

        movieList.addAll(defaultMovieList);

        // 파일에 작성
        saveMovieList(defaultMovieList);

//        System.out.println("영화 상영 스케줄표를 만들어 보자.");
        // 영화 상영 스케줄표가 존재하지 않으면 생성됨
        // 🎥 상영 스케줄 리스트 생성 (2025년 12월 12일 ~ 12월 19일 범위)
        List<MovieSchedule> defaultScheduleList = new ArrayList<>();
//        System.out.println("defaultScheduleList : " + defaultScheduleList);


        defaultScheduleList.add(new MovieSchedule(movieList.get(1), 1, Theater.GANGNAM, LocalDate.of(2025, 02, 12), LocalTime.of(10, 0), 60, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(0), 2, Theater.GANGNAM, LocalDate.of(2025, 02, 12), LocalTime.of(13, 0), 50, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(2), 3, Theater.GANGNAM, LocalDate.of(2025, 02, 12), LocalTime.of(16, 0), 45, ScreenType.FOURDX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(5), 4, Theater.KONKKUK, LocalDate.of(2025, 02, 13), LocalTime.of(10, 0), 45, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(6), 5, Theater.KONKKUK, LocalDate.of(2025, 02, 13), LocalTime.of(13, 0), 60, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(1), 6, Theater.APGUJEONG, LocalDate.of(2025, 02, 14), LocalTime.of(11, 0), 55, ScreenType.IMAX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(3), 7, Theater.APGUJEONG, LocalDate.of(2025, 02, 14), LocalTime.of(14, 0), 50, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(4), 8, Theater.APGUJEONG, LocalDate.of(2025, 02, 14), LocalTime.of(17, 0), 40, ScreenType.SCREENX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(0), 9, Theater.IPARK, LocalDate.of(2025, 02, 15), LocalTime.of(10, 30), 70, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(2), 10, Theater.IPARK, LocalDate.of(2025, 02, 15), LocalTime.of(13, 30), 60, ScreenType.FOURDX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(5), 11, Theater.GANGNAM, LocalDate.of(2025, 02, 16), LocalTime.of(10, 0), 50, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(6), 12, Theater.GANGNAM, LocalDate.of(2025, 02, 16), LocalTime.of(14, 30), 55, ScreenType.IMAX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(1), 13, Theater.KONKKUK, LocalDate.of(2025, 02, 17), LocalTime.of(11, 30), 45, ScreenType.SCREENX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(3), 14, Theater.KONKKUK, LocalDate.of(2025, 02, 17), LocalTime.of(15, 30), 40, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(4), 15, Theater.APGUJEONG, LocalDate.of(2025, 02, 18), LocalTime.of(12, 0), 65, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(2), 16, Theater.APGUJEONG, LocalDate.of(2025, 02, 18), LocalTime.of(15, 0), 55, ScreenType.FOURDX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(0), 17, Theater.IPARK, LocalDate.of(2025, 02, 19), LocalTime.of(10, 0), 60, ScreenType.IMAX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(6), 18, Theater.IPARK, LocalDate.of(2025, 02, 19), LocalTime.of(14, 30), 50, ScreenType.SCREENX));
        defaultScheduleList.add(new MovieSchedule(movieList.get(5), 19, Theater.GANGNAM, LocalDate.of(2025, 02, 19), LocalTime.of(18, 0), 45, ScreenType.TWOD));
        defaultScheduleList.add(new MovieSchedule(movieList.get(1), 20, Theater.KONKKUK, LocalDate.of(2025, 02, 19), LocalTime.of(21, 0), 35, ScreenType.TWOD));

        // 파일에 작성
        saveMovieSchedule(defaultScheduleList);
    }

    // 영화 정보 목록을 파일로 덮어 씌우는 메서드
    private void saveMovieList(List<MovieInfo> defaultMovieList) {

        ObjectOutputStream oos = null;

        try{
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream((movieFile))
                    )
            );
            for(MovieInfo m : defaultMovieList){
                oos.writeObject(m);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            try{
                if(oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 영화 상영 스케줄을 파일로 덮어 씌우는 메서드
    private void saveMovieSchedule(List<MovieSchedule> defaultScheduleList) {
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream((scheduleFile))
                    )
            );

            for(MovieSchedule s : defaultScheduleList){
                oos.writeObject(s);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (EOFException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            try{
                if(oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadMovies() {
        // 파일이 존재할 경우 파일에서 movieSchedule로 가져오기 (db -> load)
        try(ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(movieFile)
                )
        )) {
            while(true){
                movieList.add((MovieInfo) ois.readObject());
            }
        } catch(EOFException e){

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSchedules() {
        // 파일이 존재할 경우 파일에서 movieSchedule로 가져오기 (db -> load)
        try(ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(scheduleFile)
                )
        )) {
            while(true){
                movieSchedule.add((MovieSchedule) ois.readObject());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch(EOFException e){

        }catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // 영화 정보 목록 전체를 전달하는 메소드
    public List<MovieInfo> selectAllMovies() {
        return movieList;
    }
    // 상영 스케줄 표 전체를 전달하는 메소드
    public List<MovieSchedule> selectAllSchedules() {
        movieSchedule.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return movieSchedule;
    }
    // 극장 이름을 전달받아, 해당 극장에 해당하는 영화 상영 스케줄표를 전달하는 메서드
    public List<MovieSchedule> selectTheaterSchedule(Theater theater) {
        List<MovieSchedule> theaterSchedule = new ArrayList<>();

        // 필터링
        for(MovieSchedule s : movieSchedule){
            if(s.getTheaterName().equals(theater)){
                theaterSchedule.add(s);
            }
        }
        return theaterSchedule;
    }

    public List<MovieSchedule> selectedMovieInfoSchedule(int secondFilter) {
        List<MovieSchedule> movieinfoSchedule = new ArrayList<>();

        // 필터링
        for(MovieSchedule s : movieSchedule){
            if(s.getMovieInfo().getMovieNo() == secondFilter){
                movieinfoSchedule.add(s);
            }
        }
        return movieinfoSchedule;
    }

    // 날짜별 상영 스케줄 조회
    public List<MovieSchedule> selectedDateSchedule(LocalDate selectedDate) {
        List<MovieSchedule> movieinfoSchedule = new ArrayList<>();

        // 필터링
        for(MovieSchedule s : movieSchedule){
            if(s.getDate().equals(selectedDate)){
                movieinfoSchedule.add(s);
            }
        }
        return movieinfoSchedule;
    }
}