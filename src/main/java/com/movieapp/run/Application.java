package com.movieapp.run;

import com.movieapp.aggregate.Theater;
import com.movieapp.service.MovieService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final MovieService ms = new MovieService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("===== \uD83C\uDFAC CGV에 오신 것을 환영합니다! \uD83C\uDFAC =====");
            System.out.println("1. \uD83D\uDCFD\uFE0F 영화 상영 스케줄 표 보기");
            System.out.println("2. \uD83C\uDFAB 티켓 예매하기 ");
            System.out.println("3. ✅ 예매 내역 조회하기");
            System.out.println("4. 예매 내역 변경하기");
            System.out.println("5. ❌ 예매 취소하기");
            System.out.println("9. \uD83D\uDD1A 프로그램 종료");
            System.out.print("메뉴를 선택해 주세요: ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    ms.showMovieSchedule(chooseScheduleFilter());
                    break;
                case 2:
                    ms.reservTicket(inputTicketInfo());
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 9:
                    System.out.println("CGV 예매 프로그램을 종료합니다. 안녕히 가세요");
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다");
            }
        }
    }

    private static Object inputTicketInfo() {
        Scanner sc = new Scanner(System.in);
        // 영화 선택
        ms.showAllMovies();
        System.out.println("영화 번호를 선택해주세요. ");
//        ms.showAllTheaters();
        int movieNumber = sc.nextInt();
        if(movieNumber > 7){
            return null;
        }

        System.out.println();
        List<Theater> existTheaters = ms.showExistTheater(movieNumber);
        int theaterCount = 0;
        for(int i=0; i<existTheaters.size(); i++){
            System.out.println((i+1) + ". " + existTheaters.get(i).toString());
        }
        System.out.println("극장을 선택해주세요. ");
        int theaterNumber = sc.nextInt();
        if(theaterNumber > existTheaters.size()){
            return null;
        }
        Theater inputTheater = existTheaters.get(theaterNumber-1);

        // 날짜 선택
        ms.showExistDate(movieNumber, inputTheater);
        List<LocalDate> existDates = ms.showExistDate(movieNumber, inputTheater);
        for(LocalDate date : existDates){
            System.out.println(date.toString());
        }
        for(int i=0; i<existDates.size(); i++){
            System.out.println((i+1) + ". " + existDates.get(i).toString());
        }
        System.out.println("날짜를 선택해주세요. ");
        int dateNumber = sc.nextInt();


        return null;
    }

    private static int[] chooseScheduleFilter() {
        Scanner sc = new Scanner(System.in);

        // 필터링 번호, 조회 번호
        System.out.println("=========영화 상영 스케줄 표 보기=========");
        System.out.println("1. 전체 스케줄 표 확인");
        System.out.println("2. 극장 별로 조회");
        System.out.println("3. 영화 별로 조회");
        System.out.println("4. 날짜 별로 조회");

        System.out.print("조회할 내역 번호를 선택해주세요: ");
        int filterNo = sc.nextInt();
        System.out.println();

        // 필터링 번호(1 ~ 4) 가 들어옴.
        int deepFilterNo = 0;
        switch(filterNo){
            case 1:     // 전체 스케줄 표 조회
                deepFilterNo = 1;
                break;
            case 2:     // 극장 별로 조회
                int theaterNo = 1;
                for(Theater theater :  Theater.values()) {
                    System.out.println(theaterNo++ + " " + theater.toString());
                }
                System.out.println();
                System.out.print("조회할 극장 번호를 입력해주세요: ");
                deepFilterNo = sc.nextInt();
                break;
            case 3:     // 영화 별로 조회
                ms.showAllMovies();     // 영화 목록 보여주기
                System.out.println();
                System.out.print("조회할 영화 번호를 입력해주세요: ");
                deepFilterNo = sc.nextInt();
                break;
            case 4:     // 날짜 별로 조회
                //  날짜 목록(2월 12일 ~ 2월 19일)
                int dateCount = 1;
                String[] days = {"수", "목", "금", "토", "일", "월", "화", "수"};
                for(int i=12; i<=19; i++){
                    System.out.println(dateCount + ". 2월 " + i + "일" + days[dateCount-1]);
                    dateCount++;
                }
                System.out.print("조회할 날짜 번호를 입력해주세요: ");
                deepFilterNo = sc.nextInt();
                break;
        }
        return new int[]{filterNo, deepFilterNo};
    }
}