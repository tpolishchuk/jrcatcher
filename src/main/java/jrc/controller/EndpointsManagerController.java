package jrc.controller;

import jrc.domain.Endpoint;
import jrc.domain.Request;
import jrc.service.EndpointService;
import jrc.service.RequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class EndpointsManagerController {

    @Value("${endpoints.default.path}")
    private String endpointsDefaultPath;

    @Value("${pages.default.start.page.number}")
    private int currentPage;

    @Value("${pages.default.page.size}")
    private int pageSize;

    @Value("${title}")
    private String title;

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private RequestService requestService;

    private String searchQuery;

    @PostMapping("/endpoints-manager")
    public String addEndpoint(@Valid @ModelAttribute("newEndpoint") Endpoint endpoint,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        String path = endpoint.getPath().startsWith(endpointsDefaultPath)
                      ? endpoint.getPath()
                      : endpointsDefaultPath + endpoint.getPath();

        List<String> errors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            for (ObjectError globalError : bindingResult.getGlobalErrors()) {
                errors.add(globalError.getDefaultMessage());
            }

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }

            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/?error";
        }

        if (endpointService.findByPath(path) != null) {
            errors.add("Given endpoint already exists");
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/?error";
        }

        endpoint.setPath(path);
        endpoint.setActive(true);

        endpointService.addOrUpdate(endpoint);
        return "redirect:/";
    }

    @PutMapping("/endpoints-manager/{id}/active/{active}")
    public String activateDeactivateEndpoint(@PathVariable Integer id,
                                             @PathVariable Boolean active,
                                             RedirectAttributes redirectAttributes,
                                             HttpServletRequest httpServletRequest) {
        Endpoint endpoint = endpointService.findById(id);

        if (endpoint == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Endpoint does not exist");

            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:" + httpServletRequest.getHeader("referer") + "?error";
        }

        endpoint.setActive(active);
        endpointService.addOrUpdate(endpoint);
        return "redirect:" + httpServletRequest.getHeader("referer");
    }

    @DeleteMapping("/endpoints-manager/{id}")
    public String deleteEndpoint(@PathVariable Integer id,
                                 RedirectAttributes redirectAttributes) {
        Endpoint endpoint = endpointService.findById(id);

        if (endpoint == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Endpoint does not exist");

            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/?error";
        }

        endpointService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/endpoints-manager/{id}/clear")
    public String clearEndpoint(@PathVariable Integer id,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest httpServletRequest) {
        Endpoint endpoint = endpointService.findById(id);

        if (endpoint == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Endpoint does not exist");

            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:" + httpServletRequest.getHeader("referer") + "?error";
        }

        requestService.deleteByEndpointId(endpoint.getId());

        return "redirect:" + httpServletRequest.getHeader("referer");
    }

    @GetMapping("/endpoints-manager/{id}")
    public String getEndpoint(@RequestParam("query") Optional<String> query,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @PathVariable Integer id,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        searchQuery = StringUtils.EMPTY;

        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);
        query.ifPresent(q -> searchQuery = q);

        model.addAttribute("query", searchQuery);

        Endpoint endpoint = endpointService.findById(id);

        if (endpoint == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Endpoint does not exist");

            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/?error";
        }

        model.addAttribute("title", title);
        model.addAttribute("endpoint", endpoint);

        Page<Request> requestsPage;

        if (StringUtils.isBlank(searchQuery)) {
            PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);
            requestsPage = requestService.findByEndpointIdPaginated(endpoint.getId(), pageRequest);
        } else {
            PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);
            requestsPage = requestService.findByEndpointIdAndQueryPaginated(endpoint.getId(),
                                                                            searchQuery, pageRequest);
        }

        model.addAttribute("requestsPage", requestsPage);

        int totalPages = requestsPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                                 .boxed()
                                                 .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "endpoint-info";
    }
}
