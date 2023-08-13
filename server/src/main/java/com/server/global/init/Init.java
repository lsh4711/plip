// package com.server.global.init;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.context.annotation.Profile;
// import org.springframework.web.bind.annotation.RestController;

// import com.server.domain.category.entity.Category;
// import com.server.domain.category.service.CategoryService;
// import com.server.domain.member.entity.Member;
// import com.server.domain.member.entity.Member.Role;
// import com.server.domain.member.service.MemberService;
// import com.server.domain.oauth.service.KakaoTokenOauthService;
// import com.server.domain.place.entity.Place;
// import com.server.domain.place.service.PlaceService;
// import com.server.domain.record.service.S3StorageService;
// import com.server.domain.record.service.StorageService;
// import com.server.domain.region.entity.Region;
// import com.server.domain.region.service.RegionService;
// import com.server.domain.schedule.entity.Schedule;
// import com.server.domain.schedule.entity.SchedulePlace;
// import com.server.domain.schedule.service.SchedulePlaceService;
// import com.server.domain.schedule.service.ScheduleService;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Profile("default")
// @RestController
// @RequiredArgsConstructor
// public class Init {
//     // Member
//     private final MemberService memberService;

//     // Schedule
//     private final ScheduleService scheduleService;

//     // Place
//     private final PlaceService placeService;

//     // SchedulePlace
//     private final SchedulePlaceService schedulePlaceService;

//     // Category
//     private final CategoryService categoryService;

//     // Region
//     private final RegionService regionService;

//     // KakaoToken
//     private final KakaoTokenOauthService kakaoTokenOauthService;

//     // S3
//     private final StorageService storageService;

//     // @PostConstruct
//     public void init() {
//         List<Category> categories = new ArrayList<>();
//         ArrayHashMap categoryMap = new ArrayHashMap("category");

//         for (String[] entry : categoryMap) {
//             String code = entry[0];
//             String name = entry[1];
//             Category category = new Category();
//             category.setCode(code);
//             category.setName(name);
//             categories.add(category);
//         }
//         categoryService.saveCategories(categories);

//         List<Region> regions = new ArrayList<>();
//         ArrayHashMap regionMap = new ArrayHashMap("region");

//         for (String[] entry : regionMap) {
//             String engName = entry[0];
//             String korName = entry[1];
//             Region region = new Region();
//             region.setEngName(engName);
//             region.setKorName(korName);
//             regions.add(region);
//         }
//         regionService.saveRegions(regions);

//         Role admin = Role.ADMIN;
//         List<Member> members = new ArrayList<>();
//         members.add(Member.builder()
//                 .email("admin@naver.com")
//                 .password("admin1234!")
//                 .nickname("관리자")
//                 .build());
//         members.add(Member.builder()
//                 .email("lsh@naver.com")
//                 .password("lsh")
//                 .nickname("음악")
//                 .build());
//         members.add(Member.builder()
//                 .email("test-1@naver.com")
//                 .password("test1234!")
//                 .nickname("테스트-1")
//                 .build());
//         members.add(Member.builder()
//                 .email("test-2@naver.com")
//                 .password("test1234!")
//                 .nickname("테스트-2")
//                 .build());
//         members.add(Member.builder()
//                 .email("test@naver.com")
//                 .password("test1234!")
//                 .nickname("테스트")
//                 .build());

//         for (Member member : members) {
//             member.setRole(admin);
//             memberService.createMember(member);
//         }

//         for (int i = 0; i < 10; i++) {
//             Member member = Member.builder()
//                     .memberId(1L)
//                     .build();
//             Region region = new Region();
//             region.setRegionId(16L);
//             Schedule schedule = new Schedule();
//             schedule.setTitle("여행 제목");
//             schedule.setMemberCount(3);
//             schedule.setStartDate(LocalDate.now());
//             schedule.setEndDate(LocalDate.now().plusDays(3));
//             schedule.setPeriod(4);
//             schedule.setMember(member);
//             schedule.setRegion(region);
//             scheduleService.saveSchedule(schedule);
//         }

//         // scheduleService.saveSchedule(schedule);

//         String[] placeNames = {"감귤 농장", "초콜릿 박물관", "제주도 바닷가"};

//         for (int i = 1; i <= 3; i++) {
//             Category category = new Category();
//             category.setCategoryId((long)i + 1);
//             Place place = new Place();
//             place.setApiId(i * 10L + i);
//             place.setName(placeNames[i - 1]);
//             place.setAddress("제주도 무슨동 무슨길" + i);
//             place.setPhone("010-0000-0000");
//             place.setLatitude(String.format("%d.%d", i * 205 + i * 17 + i * 8, i * 27));
//             place.setLongitude(String.format("%d.%d", i * 121 + i * 23 + i * 3, i * 31));
//             place.setCategory(category);
//             placeService.savePlace(place);

//             Schedule newSchedule = new Schedule();
//             newSchedule.setScheduleId(1L);
//             Place newPlace = new Place();
//             newPlace.setPlaceId((long)i);
//             SchedulePlace schedulePlace = new SchedulePlace();
//             schedulePlace.setSchedule(newSchedule);
//             schedulePlace.setPlace(newPlace);
//             schedulePlace.setDays(1);
//             schedulePlace.setOrders(i);
//             schedulePlaceService.saveSchedulePlace(schedulePlace);
//         }

//         ((S3StorageService)storageService).resetRecordImageStorage();
//     }
// }
