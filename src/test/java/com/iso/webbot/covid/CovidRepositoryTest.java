//package com.iso.webbot.covid;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.iso.webbot.covid.domain.CovidStatus;
//import com.iso.webbot.covid.domain.CovidStatusRepository;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@DataJpaTest
//public class CovidRepositoryTest {
//
//	@Autowired
//	private CovidStatusRepository covidStatusRepository;
//	
//	@Test
//	public void whenFindByLocalAndCreatedDateBetween_thenReturnCovidStatus() {
//		//given
//		covidStatusRepository.save(CovidStatus.builder()
//												.local("서울")
//												.domesticOccurrence(123)
//												.overseasInflow(13)
//												.quarantine(450)
//												.quarantineRelease(120)
//												.dead(5)
//												.incidence(133.2)
//												.build());
//		LocalDateTime nw = LocalDateTime.now();
//		LocalDateTime fromDate = LocalDateTime.of(nw.getYear(), nw.getMonth(), nw.getDayOfMonth(), 1, 0, 0, 0);
//		LocalDateTime toDate = LocalDateTime.of(nw.getYear(), nw.getMonth(), nw.getDayOfMonth(), 23, 59, 59, 99);
//		//when
//		Optional<CovidStatus> entity = covidStatusRepository.findByLocalAndCreatedDateBetween("서울", fromDate, toDate);
//
//		assertNotNull(entity);
//		assertTrue(entity.isPresent());
//		assertEquals(entity.get().getDead(), 5);
//	}
//}
