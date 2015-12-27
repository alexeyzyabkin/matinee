package com.letionik.matinee.service;

import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.model.Participant;
import com.letionik.matinee.repository.ParticipantRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Bohdan Pohotilyi on 18.12.2015.
 */
@Service
public class MailService {
    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String MAIL_USERNAME = "mail.username";

    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private ModelMapper modelMapper;
    @Resource
    private Environment env;

    @Transactional
    public ParticipantDto addParticipantMail(ParticipantDto participantDto) {
        Participant participant = new Participant.Builder()
                .setEmail(participantDto.getEmail())
                .build();
        participantRepository.save(participant);
        return participantDto;
    }

    @Transactional
    public ParticipantDto getParticipantMail(Long participantId) {
        Participant participant = participantRepository.getOne(participantId);
        return modelMapper.map(participant, ParticipantDto.class);
    }
    @Transactional
    public void deleteParticipantMail(Long participantId) {
        participantRepository.delete(participantId);
    }

    public void sendMail(Long id, String subject) {
        Participant participant = participantRepository.getOne(id);
        if(participant.getEmail() == null){
            log.error("Participant with " + participant.getId() +" doesn't have e-mail.");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty(MAIL_USERNAME));
        message.setTo(participant.getEmail());
        switch (subject) {
            case "role":
                message.setSubject(subject);
                String roleText = "Our congratulation! Your role is - " + participant.getRole()+
                        "\n Start to prepare your costume for party.";
                message.setText(roleText);
                mailSender.send(message);
                log.info("Role was sent successfully to participant email, whose id is: " + participant.getId());
                break;
            case "tasks":
                message.setSubject(subject);
                StringBuilder tasksText = new StringBuilder();
                participant.getProgressTasks().
                        forEach(progTask -> tasksText.append(progTask.getTask().toString()));
                message.setText(tasksText.toString());
                mailSender.send(message);
                log.info("Tasks were sent successfully to participant email, whose id is: " + participant.getId());
                break;
            default:
                break;
        }
    }
}