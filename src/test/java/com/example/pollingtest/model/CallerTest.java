package com.example.pollingtest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.MultiValueMap;

import com.example.pollingtest.exceptions.BadRequestException;

public class CallerTest {
    
  @Before
  public void setUp() throws Throwable {
//    UserMappingAdapter userMappingAdapter = Mockito.mock(UserMappingAdapter.class);
//    Mockito.when(userMappingAdapter.getRefreshTokenByInternalUserId(Mockito.anyString(), Mockito.anyString()))
//        .thenReturn("1~JZ7B5Ae53FrG7YbsK05G8rW9uGcMA4c5jR8rRC6yVcuC68NgA1hINdWEXGpsw6ql");
//
//    ToolProxyProfileAdapter toolProxyProfileAdapter = Mockito.mock(ToolProxyProfileAdapter.class);
//    Mockito.when(toolProxyProfileAdapter.getBaseUrl(Mockito.anyString())).thenReturn("https://pearson.instructure.com");
//
//    String response = "{\"id\":11412262,\"description\":\"SecondAssignment\",\"due_at\":\"2016-11-08T03:00:00Z\",\"unlock_at\":null,\"lock_at\":null,\"points_possible\":100,\"grading_type\":\"points\",\"assignment_group_id\":3017929,\"grading_standard_id\":null,\"created_at\":\"2016-11-02T04:41:43Z\",\"updated_at\":\"2016-11-02T04:41:43Z\",\"peer_reviews\":false,\"automatic_peer_reviews\":false,\"position\":19,\"grade_group_students_individually\":null,\"anonymous_peer_reviews\":null,\"group_category_id\":null,\"post_to_sis\":false,\"moderated_grading\":null,\"omit_from_final_grade\":false,\"intra_group_peer_reviews\":false,\"course_id\":2040788,\"name\":\"GK-Course-02\",\"submission_types\":[\"none\"],\"has_submitted_submissions\":false,\"has_due_date_in_closed_grading_period\":false,\"is_quiz_assignment\":false,\"muted\":false,\"html_url\":\"https://pearson.instructure.com/courses/2040788/assignments/11412262\",\"has_overrides\":false,\"needs_grading_count\":0,\"integration_id\":null,\"integration_data\":{},\"published\":false,\"unpublishable\":true,\"only_visible_to_overrides\":false,\"locked_for_user\":false,\"submissions_download_url\":\"https://pearson.instructure.com/courses/2040788/assignments/11412262/submissions?zip=1\"}";
//    JSONObject result = (JSONObject) new JSONParser().parse(response);
//    CanvasAssignmentAdapter canvasAssignmentAdapter = Mockito.mock(CanvasAssignmentAdapter.class);
//    MultiValueMap<String, String> map = Mockito.any();
//    Mockito.when(canvasAssignmentAdapter.createAssignment(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), map)).thenReturn(result);
//    AssignmentFallBackService assignmentFallBackService = Mockito.mock(AssignmentFallBackService.class);
//    Mockito.when(assignmentFallBackService.createAssignmentFallBack(Mockito.anyString(), Mockito.anyString(), Mockito.any(Throwable.class)))
//        .thenThrow(new RequestRateExceedException(ErrorCodes.GS_EC_33));
//    assignmentService = new AssignmentServiceImpl(canvasAssignmentAdapter, new AssignmentPayLoadTransformerImpl(), assignmentFallBackService);
  }

  @Test
  public void testCreateAssignment() {
//    String assignmentPayLoad = "{\"mappings\":{\"internalInstructorId\":\"ffffffff548f61efe4b05a12806d680e3\",\"internalCourseId\":\"revel-59d1-4ef2-89a9-85c42cec559c\",\"internalAssignmentId\":\"5790d30de4b01e71ca7132090001vx1q\",\"associationId\":\"randdev2-4307-47e8-b73d-1a20dac128f9\"},\"lineItem\":{\"itemId\":\"11420834\",\"availabilityDate\":null,\"dueDate\":\"2016-10-08T03:00:00Z\",\"title\":\"G3\",\"pointsPossible\":\"100\",\"description\":\"G3\",\"status\":null,\"scaleType\":\"PERCENTAGE\"},\"assignmentSyncType\":\"ASSIGNMENTGROUPS\",\"extendedProperties\":{\"metadata\":{\"gridAssetId\":\"f01d48f0-8c67-11e6-a861-dd724bfd202c\",\"assetId\":\"d7e821fd-88cd-41d2-b408-2bf204fdbaad\"},\"treeVersion\":1,\"inProgress\":true,\"liveAssessments\":[\"http://repo.paf.cert.pearsoncmg.com/paf-repo/resources/activities/1f4cad76-510d-44b5-85a2-3749c5844af8\"],\"sources\":[\"COURSE_BOOK\"],\"learningContextId\":1000000121740,\"ctrldAcsActivity\":true,\"type\":\"revel\",\"version\":5,\"states\":[{\"updatedBy\":\"ffffffff57f4ed29e4b05cc8e8a93d76\",\"updatable\":null,\"type\":\"UNPUBLISHED\",\"updatedAt\":\"2016-10-07T08:27:41.273Z\"},{\"updatedBy\":\"ffffffff57f4ed29e4b05cc8e8a93d76\",\"updatable\":null,\"type\":\"PUBLISHED\",\"updatedAt\":\"2016-10-07T08:27:50.143Z\"},{\"updatedBy\":\"ffffffff57f4ed29e4b05cc8e8a93d76\",\"updatable\":null,\"type\":\"UNUPDATABLE\",\"updatedAt\":\"2016-10-07T08:27:50.183Z\"},{\"updatedBy\":\"ffffffff57f4ed29e4b05cc8e8a93d76\",\"updatable\":null,\"type\":\"RELEASED\",\"updatedAt\":\"2016-10-07T08:27:50.268Z\"}],\"createdAt\":\"2016-10-07T08:27:41.273Z\",\"updatable\":false,\"launchUri\":\"https://revel-ilp-stg.pearson.com/#/courses/57f75c2ee4b0ffddcc31649f/assignments?assignmentId=d7e821fd-88cd-41d2-b408-2bf204fdbaad\",\"ungradablePoints\":0,\"thumbnailUri\":\"https://revel-ilp-stg.pearson.com/eps/sanvan/api/item/ea62f370-193f-49e9-9ce7-22b8d3d26b98/1/file/images/course_background.jpg\",\"released\":true,\"updatedAt\":\"2016-10-07T08:29:34.191Z\"}}";
//    AssignmentDTO dto = assignmentService.createAssignment("2040788", assignmentPayLoad);
//    assertNotNull(dto);
//    assertEquals("11412262", dto.getLineItem().getItemId());
//    assertEquals("100", dto.getLineItem().getPointsPossible());
//    assertEquals("GK-Course-02", dto.getLineItem().getTitle());
//    assertEquals("2016-11-08T03:00:00Z", dto.getLineItem().getDueDate());
  }

//  @Test(expected = BadRequestException.class)
  public void testBadRequestMissingExternalCourseId() {
//    String assignmentPayLoad = "{\"mappings\":{\"internalInstructorId\":\"ffffffff548f61efe4b05a12806d680e3\",\"internalCourseId\":\"revel-59d1-4ef2-89a9-85c42cec559c\",\"internalAssignmentId\":\"5790d30de4b01e71ca7132090001vx1q\",\"associationId\":\"randdev2-4307-47e8-b73d-1a20dac128f9\"},\"lineItem\":{\"itemId\":\"11420834\",\"availabilityDate\":null,\"dueDate\":\"2016-10-08T03:00:00Z\",\"title\":\"G3\",\"pointsPossible\":\"100\",\"description\":\"G3\",\"status\":null,\"scaleType\":\"PERCENTAGE\"},\"assignmentSyncType\":\"ASSIGNMENTGROUPS\",\"extendedProperties\":{\"metadata\":{\"gridAssetId\":\"f01d48f0-8c67-11e6-a861-dd724bfd202c\",\"assetId\":\"d7e821fd-88cd-41d2-b408-2bf204fdbaad\"},\"treeVersion\":1,\"inProgress\":true,\"liveAssessments\":[\"http://repo.paf.cert.pearsoncmg.com/paf-repo/resources/activities/1f4cad76-510d-44b5-85a2-3749c5844af8\"],\"sources\":[\"COURSE_BOOK\"],\"learningContextId\":1000000121740,\"ctrldAcsActivity\":true,\"type\":\"revel\",\"version\":5,\"states\":[{\"updatedBy\":\"ffffffff57f4ed29e4b05cc8e8a93d76\",\"updatable\":null,\"type\":\"UNPUBLISHED\",\"updatedAt\":\"2016-10-07T08:27:41.273Z\"},{\"updatedBy\":\"ffffffff57f4ed29e4b05cc8e8a93d76\",\"updatable\":null,\"type\":\"PUBLISHED\",\"updatedAt\":\"2016-10-07T08:27:50.143Z\"},{\"updatedBy\":\"ffffffff57f4ed29e4b05cc8e8a93d76\",\"updatable\":null,\"type\":\"UNUPDATABLE\",\"updatedAt\":\"2016-10-07T08:27:50.183Z\"},{\"updatedBy\":\"ffffffff57f4ed29e4b05cc8e8a93d76\",\"updatable\":null,\"type\":\"RELEASED\",\"updatedAt\":\"2016-10-07T08:27:50.268Z\"}],\"createdAt\":\"2016-10-07T08:27:41.273Z\",\"updatable\":false,\"launchUri\":\"https://revel-ilp-stg.pearson.com/#/courses/57f75c2ee4b0ffddcc31649f/assignments?assignmentId=d7e821fd-88cd-41d2-b408-2bf204fdbaad\",\"ungradablePoints\":0,\"thumbnailUri\":\"https://revel-ilp-stg.pearson.com/eps/sanvan/api/item/ea62f370-193f-49e9-9ce7-22b8d3d26b98/1/file/images/course_background.jpg\",\"released\":true,\"updatedAt\":\"2016-10-07T08:29:34.191Z\"}}";
//    assignmentService.createAssignment("", assignmentPayLoad);
  }
    
}
