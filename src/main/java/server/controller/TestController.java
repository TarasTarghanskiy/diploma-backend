package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.entity.StoryEntity;
import server.repository.StoryRepository;
import server.util.ScheduledTasks;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    @Autowired
    private StoryRepository storyRepository;

    @GetMapping("/start")
    public void accountAvatar() {
    }
}
