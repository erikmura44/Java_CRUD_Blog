package blog.controllers;

import blog.forms.PostForm;
import blog.forms.EditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import blog.models.*;
import blog.services.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PostsController {
    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notifyService;

    @RequestMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "posts/view";
    }


    @RequestMapping("/posts")
    public String index(Model model) {
        List<Post> allPosts = postService.findAll();
        model.addAttribute("allPosts", allPosts);
        return "posts/posts";
    }

    @RequestMapping("/posts/create")
    public String form(PostForm postForm){
        return "posts/create";
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    public String formPage(PostForm postForm){


        Post post = new Post((long)7, postForm.getTitle(), postForm.getBody(), new User((long)3, "user1", "1234"));
        postService.create(post);

        return "redirect:/";
    }

    @RequestMapping("/posts/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, EditForm editForm) {
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @RequestMapping(value = "/posts/edit/{id}", method = RequestMethod.POST)
    public String editPage(@PathVariable("id") Long id, EditForm editForm){

        Post post = new Post(id, editForm.getTitle(), editForm.getBody(), new User((long)3, "user1", "1234"));
        postService.edit(post);

        return "redirect:/";
    }

    @RequestMapping(value = "/posts/delete/{id}", method = RequestMethod.POST)
    public String deletePost(@PathVariable("id") Long id){
        postService.deleteById(id);
        return "redirect:/";
    }

}
