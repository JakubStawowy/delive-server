package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.dao.AnnouncementService;
import com.example.rentiaserver.security.api.IAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = AnnouncementRemoveController.BASE_ENDPOINT)
public final class AnnouncementRemoveController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/announcements";
    private final AnnouncementService announcementService;
    private final IAuthorizeService authorizeService;

    @Autowired
    public AnnouncementRemoveController(AnnouncementService announcementService, IAuthorizeService authorizeService) {
        this.announcementService = announcementService;
        this.authorizeService = authorizeService;
    }

    @DeleteMapping(value = EndpointConstants.REMOVE_ANNOUNCEMENT_ENDPOINT)
    public ResponseEntity<Boolean> removeAnnouncement(@PathVariable("id") Long id, @RequestParam("userId") Long userId, @RequestParam("password") String password) {
        try {
            if(authorizeService.authorizeUserWithIdAndPassword(userId, password)) {
                announcementService.deleteById(id);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (EmptyResultDataAccessException ignored) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
