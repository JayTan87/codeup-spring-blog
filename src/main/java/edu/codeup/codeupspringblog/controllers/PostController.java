package edu.codeup.codeupspringblog.controllers;

import edu.codeup.codeupspringblog.models.Post;
import edu.codeup.codeupspringblog.models.User;
import edu.codeup.codeupspringblog.repositories.ContactRepository;
import edu.codeup.codeupspringblog.repositories.PostRepository;
import edu.codeup.codeupspringblog.repositories.UserRepository;
import edu.codeup.codeupspringblog.services.CountSvc;
import edu.codeup.codeupspringblog.services.EmailSvc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostRepository postsDao;
    private UserRepository usersDao;
    private EmailSvc emailSvc;

    private CountSvc countSvc;

    public PostController(PostRepository postsDao, UserRepository usersDao, EmailSvc emailSvc, CountSvc countSvc) {
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.emailSvc = emailSvc;
        this.countSvc = countSvc;
    }

    //    public String returnPost(Model model) {
//        model.addAttribute("posts", postsDao.findAll());
//        return "/index";
//    }
    @GetMapping("")
    public String indexPage(Model model) {
        model.addAttribute("posts", postsDao.findAll());
        return "posts/index";
    }
    @GetMapping("/{id}")
    public String viewIndividualPost(@PathVariable long id,Model model) {
        if(postsDao.existsById(id)) {
            Post post = postsDao.findById(id).get();
            model.addAttribute("post", post);
            return "posts/singlepost";
        }
        return "redirect:/posts";
    }
    @GetMapping("/{id}/edit")
    public String editIndividualPost(@PathVariable long id,Model model) {
        if(postsDao.existsById(id)) {
            Post post = postsDao.findById(id).get();
            model.addAttribute("post", post);
            return "posts/edit";
        }
        return "redirect:/posts";
    }
    @PostMapping("/edit")
    public String updatePost(@ModelAttribute Post post) {
        User hardCoded = usersDao.findById(1L).get();
        Post updatedPost = new Post(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                hardCoded
                );
       postsDao.save(updatedPost);
        return "redirect:/posts";
    }

    @GetMapping("/create")
    public String showCreatePostView(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "posts/create";
    }
    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post) {
        post.setUser(usersDao.findById(1L).get());
        postsDao.save(post);
        emailSvc.prepareAndSend(post, "You have created an Ad", "Here is information regarding your ad");
        return "redirect:/posts";
    }

    @GetMapping("/users/posts/count")
    @ResponseBody
        public long returnPostsUsersCount() {
            return countSvc.returnPostsUsersCount();
        }

}
