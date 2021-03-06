package org.bigbluebutton.conference.service.recorder.participants;

import java.util.HashMap;

import org.bigbluebutton.conference.IRoomListener;
import org.bigbluebutton.conference.Participant;
import org.bigbluebutton.conference.service.recorder.RecorderApplication;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

public class ParticipantsEventRecorder implements IRoomListener {
	private static Logger log = Red5LoggerFactory.getLogger(ParticipantsEventRecorder.class, "bigbluebutton");
	private final RecorderApplication recorder;
	private final String session;
	
	String name = "RECORDER:PARTICIPANT";
		
	public ParticipantsEventRecorder(String session, RecorderApplication recorder) {
		this.recorder = recorder;
		this.session = session;
	}

	@Override
	public void endAndKickAll() {
		ParticipantEndAndKickAllRecordEvent ev = new ParticipantEndAndKickAllRecordEvent();
		ev.setTimestamp(System.currentTimeMillis());
		ev.setMeetingId(session);
		recorder.record(session, ev);		
	}

	@Override
	public void participantJoined(Participant p) {
		ParticipantJoinRecordEvent ev = new ParticipantJoinRecordEvent();
		ev.setTimestamp(System.currentTimeMillis());
		ev.setUserId(p.getUserid().toString());
		ev.setMeetingId(session);
		ev.setStatus(p.getStatus().toString());
		ev.setRole(p.getRole());

		recorder.record(session, ev);
	}

	@Override
	public void participantLeft(Long userid) {
		ParticipantLeftRecordEvent ev = new ParticipantLeftRecordEvent();
		ev.setTimestamp(System.currentTimeMillis());
		ev.setUserId(userid.toString());
		ev.setMeetingId(session);
		
		recorder.record(session, ev);
	}

	@Override
	public void participantStatusChange(Long userid, String status, Object value) {
		ParticipantStatusChangeRecordEvent ev = new ParticipantStatusChangeRecordEvent();
		ev.setTimestamp(System.currentTimeMillis());
		ev.setUserId(userid.toString());
		ev.setMeetingId(session);
		ev.setStatus(status);
		ev.setValue(value.toString());
		
		recorder.record(session, ev);
	}

	@Override
	public String getName() {
		return this.name;
	}

}
