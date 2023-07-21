package com.server.domain.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.category.entity.Category;
import com.server.domain.category.repository.CategoryRepository;
import com.server.domain.place.entity.Place;
import com.server.domain.place.repository.PlaceRepository;
import com.server.domain.record.entity.Record;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.repository.SchedulePlaceRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceService {
	private final PlaceRepository placeRepository;

	private final SchedulePlaceRepository schedulePlaceRepository;

	private final CategoryRepository categoryRepository;

	public List<Place> savePlaces(List<Place> places) {
		return placeRepository.saveAll(places);

		// 디버깅용
		// for (Place place : places) {
		//     Place savedPlace = placeRepository.save(place);
		//     System.out.println("CREATED");
		// }
	}

	public List<SchedulePlace> savePlaceLists(Schedule savedSchedule, List<List<Place>> placeLists) {
		if (placeLists == null) {
			return null;
		}

		List<SchedulePlace> schedulePlaces = new ArrayList<>();

		for (int i = 0; i < placeLists.size(); i++) {
			List<Place> places = placeLists.get(i);
			// if (places.size() == 0) {
			//     throw new CustomException(ExceptionCode.EMPTY_PLACES);
			// }
			// 나중에 Map API 데이터에서 유니크 값들을 확인하고 중복 저장 방지 필요
			for (int j = 0; j < places.size(); j++) {
				Place place = places.get(j);
				String code = place.getCategory().getCode();
				Category category = categoryRepository.findByCode(code);
				place.setCategory(category);
				placeRepository.save(place);

				Boolean bookmark = place.getBookmark();
				SchedulePlace schedulePlace = new SchedulePlace();
				schedulePlace.setSchedule(savedSchedule);
				schedulePlace.setPlace(place);
				schedulePlace.setDays(i + 1);
				schedulePlace.setOrders(j + 1);
				schedulePlace.setBookmark(bookmark); // 로직 변경되면 null 처리 해야함
				schedulePlaceRepository.save(schedulePlace);
				schedulePlaces.add(schedulePlace);
			}
		}

		return schedulePlaces;
	}

	// 소셜 확장 시 사용됨, 현재 PlaceController에서 숨김
	public List<Record> findRecords(Long placeId) {
		List<Record> foundRecords = new ArrayList<>();
		List<SchedulePlace> schedulePlaceList = schedulePlaceRepository.findByPlacePlaceId(placeId);

		if (schedulePlaceList.size() == 0) {
			throw new CustomException(ExceptionCode.NO_VISITOR);
		}
		for (SchedulePlace schedulePlace : schedulePlaceList) {
			List<Record> records = schedulePlace.getRecords();
			foundRecords.addAll(records);
		}

		return foundRecords;
	}
}
