package com.optile.scheduler.optilescheduler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OptileSchedulerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("development")
class OptileSchedulerApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testPushJob_Success() throws Exception {
		String req = "{\"name\":\"sendEmail\",\"startTime\":1579361924206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"text\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":1,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":true}";
		String res =  "{\"name\":\"sendEmail\",\"startTime\":1579361924206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"text\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":1,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":true,\"valid\":true}";
		mockMvc.perform(post("/pushjob").content(req).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk()).andExpect(content().json(res));
	}
	
	@Test
	public void testFindAllJobs_Success() throws Exception {
		String res = "[{\"name\":\"sendEmail\",\"startTime\":1579361924206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"text\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":1,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":true,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579364024206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":3,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579361924206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":1,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579362224206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":2,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579362524206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":3,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579362824206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":4,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579363124206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":5,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579363424206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":1,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579363724206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":2,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579364324206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":4,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true},{\"name\":\"sendEmail\",\"startTime\":1579364624206,\"config\":\"{\\\"to\\\":\\\"abdelrhman.fathy@gmail.com\\\",\\\"body\\\":\\\"test email body\\\", \\\"subject\\\":\\\"test email subject\\\"}\",\"priority\":5,\"validateURL\":\"http://localhost:8085/validateEmailParam\",\"postURL\":\"http://localhost:8085/sendEmail\",\"immediateJob\":false,\"valid\":true}]";
		mockMvc.perform(get("/jobs")).andExpect(status().isOk()).andExpect(content().json(res));
	}

	@Test
	void contextLoads() {
	}
	
	

}
