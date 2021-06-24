package com.rorhcdream.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rorhcdream.springboot.domain.post.Post;
import com.rorhcdream.springboot.domain.post.PostRepository;
import com.rorhcdream.springboot.web.dto.post.PostSaveRequestDto;
import com.rorhcdream.springboot.web.dto.post.PostUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
    }

    @WithMockUser
    @Test
    public void Post_fetched() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        Post savedPost = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build()
        );
        Long id = savedPost.getId();

        String url = "http://localhost:" + port + "/api/v1/post/" + id;

        //when
        mvc.perform(get(url).param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.content", is(content)))
                .andExpect(jsonPath("$.author", is(author)));
    }

    @WithMockUser
    @Test
    public void Post_modified() throws Exception {
        //given
        Post savedPost = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );

        Long updateId = savedPost.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/post/" + updateId;

        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk());

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @WithMockUser
    @Test
    public void Post_registered() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "author";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/v1/post";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk());

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
    }
}
