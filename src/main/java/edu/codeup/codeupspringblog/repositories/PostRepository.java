package edu.codeup.codeupspringblog.repositories;


import edu.codeup.codeupspringblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

}
