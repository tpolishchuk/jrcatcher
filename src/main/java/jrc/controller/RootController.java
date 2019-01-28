package jrc.controller;

import jrc.domain.Endpoint;
import jrc.service.EndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RootController {

    @Autowired
    private EndpointService endpointService;

    @Value("${title}")
    private String title;

    @Value("${pages.default.start.page.number}")
    private int currentPage;

    @Value("${pages.default.page.size}")
    private int pageSize;

    @GetMapping("/")
    public String getRoot(@RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size,
                          Model model) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        model.addAttribute("title", title);
        model.addAttribute("newEndpoint", new Endpoint());

        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);
        Page<Endpoint> endpointsPage = endpointService.findAllPaginated(pageRequest);

        model.addAttribute("endpointsPage", endpointsPage);

        int totalPages = endpointsPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                                 .boxed()
                                                 .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "root";
    }
}
