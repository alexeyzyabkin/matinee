package com.letionik.matinee.controller;

import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Bohdan Pohotilyi on 21.12.2015.
 */
@RestController
@RequestMapping(value = "/email")
public class MailController {
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ParticipantDto addParticipantMail(@RequestBody ParticipantDto participantDto) {
        return mailService.addParticipantMail(participantDto);
    }

    @RequestMapping(value = "/get/{participantId}", method = RequestMethod.GET)
    public ParticipantDto getParticipantMail(@PathVariable Long participantId) {
        return mailService.getParticipantMail(participantId);
    }


    @RequestMapping(value = "/delete/{participantId}", method = RequestMethod.DELETE)
    public ResponseEntity<ParticipantDto> deleteParticipantMail(@PathVariable Long participantId) {
        final ParticipantDto particiant = mailService.getParticipantMail(participantId);
        if (particiant != null) {
            mailService.deleteParticipantMail(participantId);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
