package com.iso.webbot.covid;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.snippet.Attributes.key;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iso.webbot.ApiDocumentUtils;
import com.iso.webbot.covid.dto.CovidStatusSaveDto;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs//(uriScheme = "https", uriHost = "docs.api.com")
@AutoConfigureMockMvc
@Transactional
public class CovidControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
//	@MockBean
//	private CovidService covidService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
										.addFilters(new CharacterEncodingFilter("UTF-8", true))
										.apply(documentationConfiguration(restDocumentationContextProvider))
										.alwaysDo(print())
										.build();
	}
		
	@Test
	public void getDataTest() throws Exception {
		//given
		CovidStatusSaveDto dto = CovidStatusSaveDto.builder()
											.local("서울")
											.domesticOccurrence(123)
											.overseasInflow(13)
											.quarantine(450)
											.quarantineRelease(120)
											.dead(5)
											.incidence(133.2)
											.build();
		mockMvc.perform(post("/api/covid-search")
								.content(objectMapper.writeValueAsString(dto))
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
				);
		
		//when
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		params.add("local", "서울");
		
		LocalDateTime nw = LocalDateTime.now();
		params.add("date", nw.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		ResultActions result = mockMvc.perform(get("/api/covid-search")
										.params(params)
										.accept(MediaType.APPLICATION_JSON)
				);
		;
		//then
		result.andExpect(status().isOk())
				.andDo(document("covid-status-get",
						ApiDocumentUtils.getDocumentRequest(),
						ApiDocumentUtils.getDocumentResponse(),
						requestParameters(
								parameterWithName("local").description("지역"),
								parameterWithName("date").description("날짜").attributes(key("format").value("yyyyMMdd"))
								),
						responseFields(
							fieldWithPath("success").description("성공 여부"), 
							fieldWithPath("code").description("결과 코드"),
							fieldWithPath("msg").description("결과 메세지"),
							fieldWithPath("data.local").description("지역"),
							fieldWithPath("data.totalChangeConfirmedPatients").description("총 추가확진자"),
							fieldWithPath("data.domesticOccurrence").description("국내유입"),
							fieldWithPath("data.overseasInflow").description("해외유입"),
							fieldWithPath("data.confirmedPatients").description("총 확진자"),
							fieldWithPath("data.quarantine").description("격리 중"),
							fieldWithPath("data.quarantineRelease").description("격리 해제"),
							fieldWithPath("data.dead").description("사망자"),
							fieldWithPath("data.incidence").description("비율")
								)
						));
	}
	
	@Test
	public void postDataTest() throws Exception {
		//given
		CovidStatusSaveDto dto = CovidStatusSaveDto.builder()
													.local("서울")
													.domesticOccurrence(123)
													.overseasInflow(13)
													.quarantine(450)
													.quarantineRelease(120)
													.dead(5)
													.incidence(133.2)
													.build();
		
		//when
		 ResultActions result = mockMvc.perform(post("/api/covid-search")
						.content(objectMapper.writeValueAsString(dto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
				);
		
		//then
		result.andExpect(status().isOk())
				.andDo(document("covid-status-save",
				ApiDocumentUtils.getDocumentRequest(),
				ApiDocumentUtils.getDocumentResponse(),
				requestFields(
						fieldWithPath("local").type(JsonFieldType.STRING).description("지역"),
						fieldWithPath("domesticOccurrence").type(JsonFieldType.NUMBER).description("국내유입"),
						fieldWithPath("overseasInflow").type(JsonFieldType.NUMBER).description("해외유입"),
						fieldWithPath("quarantine").type(JsonFieldType.NUMBER).description("격리 중"),
						fieldWithPath("quarantineRelease").type(JsonFieldType.NUMBER).description("격리 해제"),
						fieldWithPath("dead").type(JsonFieldType.NUMBER).description("사망자"),
						fieldWithPath("incidence").type(JsonFieldType.NUMBER).description("비율")
							),
				responseFields( 
						fieldWithPath("success").description("성공 여부"), 
						fieldWithPath("code").description("결과 코드"),
						fieldWithPath("msg").description("결과 메세지"),
						fieldWithPath("data").description("식별자")
						)
				)); 
	}
}
