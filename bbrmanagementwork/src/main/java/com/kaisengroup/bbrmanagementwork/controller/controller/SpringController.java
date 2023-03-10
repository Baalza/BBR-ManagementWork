package com.kaisengroup.bbrmanagementwork.controller.controller;

import com.kaisengroup.bbrmanagementwork.controller.model.Component;
import com.kaisengroup.bbrmanagementwork.controller.model.FilterClient;
import com.kaisengroup.bbrmanagementwork.controller.model.User;
import com.kaisengroup.bbrmanagementwork.controller.model.Work;
import com.kaisengroup.bbrmanagementwork.controller.model.WorkSheet;
import com.kaisengroup.bbrmanagementwork.controller.repository.ComponentRepository;
import com.kaisengroup.bbrmanagementwork.controller.repository.UserRepository;
import com.kaisengroup.bbrmanagementwork.controller.repository.WorkRepository;
import com.kaisengroup.bbrmanagementwork.controller.repository.WorkSheetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.data.crossstore.ChangeSetPersister;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javassist.expr.NewArray;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
// @RequestMapping("/bbr-workmanagement")
public class SpringController {

    @ModelAttribute(value = "work")
    public Work getWork() {
        return new Work();
    }
    @ModelAttribute(value = "cliente")
    public FilterClient getcliente() {
       return new FilterClient();
    }
    @ModelAttribute(value = "component")
    public Component getComponent() {
       return new Component();
    }
    @ModelAttribute(value = "worksheet")
    public WorkSheet getWorkSheet() {
       return new WorkSheet();
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ComponentRepository componentRepository ;
    @Autowired
    private WorkSheetRepository worksheetRepository ;

    @GetMapping("/all")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/index")
    public String loadInfo(Model modelUser, Model modelWorks, Model modelWorkA, Model modelWorkB,
            HttpServletRequest req, Model modelUrl) {
        String baseUrl = "http://localhost:8080/bbr-workmanagement/login";
        modelUrl.addAttribute("baseUrl", baseUrl);
        modelUser.addAttribute("users", userRepository.findAll());
        modelWorks.addAttribute("works", workRepository.findAll());
        modelWorkA.addAttribute("worksA", workRepository.findByStatusTrue());
        modelWorkB.addAttribute("worksB", workRepository.findByStatusFalse());
    
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest req, Model modelUrl) {
        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath();
        modelUrl.addAttribute("baseUrl", baseUrl);
        return "login";
    }

    @GetMapping("/workDashboard")
    public String archivio() {

        return "workDashboard.html";
    }

    @PostMapping("/addWork")
    public String addWork(@Valid @ModelAttribute("work") Work work, Model model) {
        Component component = new Component();
        LocalDate localDate = LocalDate.now();

        String data = localDate.toString();
        work.setData(data);
        work.setStatus(true);
        model.addAttribute("work", work);
        workRepository.save(work);
        if(work.getType().equals("ordine")){
            component.setName(work.getName());
            component.setData(work.getData());
            component.setFkwork(work.getId());
            componentRepository.save(component);
        }

        return "redirect:/index";
    }

    @GetMapping("/work/active/dashboard/{id}")
    public String loadWorkActive(@PathVariable("id") int id, Model modelWorks, Model modelComponent) {
        workRepository.findById(id).ifPresent(o -> modelWorks.addAttribute("work", o));
        modelComponent.addAttribute("components", componentRepository.findByFkworkOrderByIdAsc(id));
        // System.out.println("id: " + id);
        return "workDashboard";
    }
    @GetMapping("/work/delete/{id}")
    public String deleteWork(@PathVariable("id") int id, Model modelWorks) {
        Work work = workRepository.findById(id).get();
        workRepository.delete(work);
        return "redirect:/index";
    }
    @GetMapping("/work/update-status/{id}")
    public String updateStatusWork(@PathVariable("id") int id, Model modelWorks) {
        Work work = workRepository.findById(id).get();
        work.setStatus(false);
        workRepository.save(work);
        return "redirect:/index";
    }
    @PostMapping("/work/update/{id}")
    public String updateWork(@PathVariable("id") int id, @Valid @ModelAttribute("work") Work work, Model model) {
        Work workT = workRepository.findById(id).get();
        String data = workT.getData().toString();
        Boolean status = workT.getStatus();
        model.addAttribute("work", work);
        workT = work;
        workT.setData(data);
        workT.setStatus(status);
        workRepository.save(workT);

        return "redirect:/work/active/dashboard/{id}";
    }
    @GetMapping("/work/archivied/dashboard/{id}")
    public String loadWorkArchivied(@PathVariable("id") int id, Model modelWorks) {
        workRepository.findById(id).ifPresent(o -> modelWorks.addAttribute("work", o));
        // System.out.println("id: " + id);
        return "archiviedWork";
    }
    @GetMapping("/work/archivied/update-status/{id}")
    public String updateStatusArchiviedWork(@PathVariable("id") int id, Model modelWorks) {
        Work work = workRepository.findById(id).get();
        work.setStatus(true);
        workRepository.save(work);
        return "redirect:/index";
    }
    @GetMapping("/work/archivied")
    public String loadInfoArchivied(Model modelWorkB, Model modelClienti, Model modelFilterClient) {
        FilterClient c = new FilterClient();
        modelWorkB.addAttribute("worksB", workRepository.findByStatusFalse());
        modelClienti.addAttribute("clienti", workRepository.findDistinctCliente( ));
        modelFilterClient.addAttribute("cliente", c);
        return "archivio";
    }
    /*@GetMapping("/work/archivied/filter/{denominazione}")
    public String filterAchivied(@RequestParam(value = "denominazione") String den, Model modelWorks) {
       
        return "archivio";
    }*/
    @PostMapping("/work/archivied/filter")
    public String filterArchiviedWork(@Valid @ModelAttribute("cliente") FilterClient cliente, Model model,RedirectAttributes name,RedirectAttributes type,RedirectAttributes clienteC) {
       
        model.addAttribute("cliente", cliente);
        //return "redirect:/work/archivied/filter?search:" + cliente.getName() + "?type:" + cliente.getType() + "?cliente:" + cliente.getCliente();
       // name.addAttribute("name",cliente);
       // type.addAttribute("type", cliente.getType());
       // clienteC.addAttribute("cliente", cliente.getCliente());
        //return "redirect:/work/archivied/filter";
        return "redirect:/work/archivied/filter?search=" + cliente.getName() + "&type=" + cliente.getType() + "&cliente=" + cliente.getCliente();
    }
    @GetMapping("/work/archivied/filter")
    public String loadInfoArchiviedFilter(HttpServletRequest request,Model modelClienti, Model modelWorkB, Model modelFilterClient) {
        modelClienti.addAttribute("clienti", workRepository.findDistinctCliente( ));
        FilterClient c = new FilterClient();
       
        String search = request.getParameter("search");
        String type = request.getParameter("type");
        String cliente = request.getParameter("cliente");
        c.setName(search);
        c.setType(type);
        c.setCliente(cliente);
        System.out.println(c.getName());
        List <Work> workFilteredT = new ArrayList<Work>();
        List <Work> workFilteredC = new ArrayList<Work>();
        List <Work> workSearched = new ArrayList<Work>();
        List <Work> workArchivied = new ArrayList<Work>();
        workArchivied = workRepository.findByStatusFalse();
        if(!search.equals("")){
            for (Work work : workArchivied) {
                if(work.getName().toLowerCase().contains(search.toLowerCase())){
                    workSearched.add(work);
                   
                }
            }
        }
        if(!cliente.equals("") && !type.equals("")){
            workFilteredT = workRepository.findAllByTypeAndCliente(type, cliente);
        }else if(cliente.equals("") && !type.equals("")){
            workFilteredT = workRepository.findByType(type);
        }else if(!cliente.equals("") && type.equals("")){
            workFilteredT = workRepository.findByCliente(cliente);
        }
        /*List<Work> threeCopy = new ArrayList<>(workFilteredT);
        
        for (Work work : workSearched) {
            threeCopy.remove(work);
        }
       workFilteredT.removeAll(threeCopy);*/
       if(workSearched.size() == 0 && workFilteredT.size()==0){
        modelWorkB.addAttribute("worksB", workArchivied);
       }else if(workSearched.size() == 0 && workFilteredT.size()>0){
        modelWorkB.addAttribute("worksB", workFilteredT);//change
    }else if(workSearched.size() > 0 && workFilteredT.size()==0){
        modelWorkB.addAttribute("worksB", workSearched);//change
    }else{
        for (Work work : workSearched) {
            for (Work work2 : workFilteredT) {
                if(work.equals(work2)){
                    workFilteredC.add(work2);
                }
            }
        }
        modelWorkB.addAttribute("worksB", workFilteredC);//change
    }
        modelFilterClient.addAttribute("cliente", c);
        return "archivio";
    }
    @PostMapping("/add/component/{id}")
    public String addComponent(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model, Model modelWorks) {
        LocalDate localDate = LocalDate.now();
        String data = localDate.toString();
        Work work = new Work();
        workRepository.findById(id).ifPresent(o -> modelWorks.addAttribute("work", o));
        model.addAttribute("component", component);
        //workT = work;
        component.setData(data);
        component.setFkwork(id);
        componentRepository.save(component);

        return "redirect:/work/active/dashboard/{id}";
    }
    @GetMapping("/component/dashboard/{id}")
    public String loadComponent(@PathVariable("id") int id, Model modelWorks, Model modelComponent, Model worksheet) {
        Component comp = new Component();
        componentRepository.findById(id).ifPresent(o -> modelComponent.addAttribute("component", o));
        componentRepository.findById(id).ifPresent(o -> comp.setFkwork(o.getFkwork()));
        int fk = comp.getFkwork();
        workRepository.findById(fk).ifPresent(o -> modelWorks.addAttribute("work", o));
        worksheet.addAttribute("worksheets", worksheetRepository.findByFkcompOrderByIdAsc(id));
        
        // System.out.println("id: " + id);
        return "componentDashboard";
    }
    @GetMapping("/component/delete/{id}")
    public String deleteComponent(@PathVariable("id") int id, Model modelComponent) {
        Component component = componentRepository.findById(id).get();
        Work work = workRepository.findById(component.getFkwork()).get();
        componentRepository.delete(component);
        if(work.getType().equals("ordine")){
            workRepository.delete(work);
        }
        return "redirect:/index";
    }
    @PostMapping("/component/update/{id}")
    public String updateComponent(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
        String data = comp.getData().toString();
        int fk = comp.getFkwork();
        model.addAttribute("component", component);
        comp = component;
        comp.setData(data);
        comp.setFkwork(fk);
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/price/{id}")
    public String updatePrice(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setPrezzo(component.getPrezzo());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/material/{id}")
    public String updateMaterial(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setMateriale(component.getMateriale());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/furniture/{id}")
    public String updateFurniture(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setFornitore(component.getFornitore());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/peso/{id}")
    public String updatePeso(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setPeso(component.getPeso());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/costruiti/{id}")
    public String updateCostruiti(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setCostruiti(component.getCostruiti());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/consegnati/{id}")
    public String updateConsegnati(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setConsegnati(component.getConsegnati());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/magazzino/{id}")
    public String updateMagazzino(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setMagazzino(component.getMagazzino());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/lavesterne/{id}")
    public String updateLavEsterne(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setLavesterne(component.getLavesterne());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/component/update/annotazioni/{id}")
    public String updateAnnotazioni(@PathVariable("id") int id, @Valid @ModelAttribute("component") Component component, Model model) {
        Component comp = componentRepository.findById(id).get();
       
        model.addAttribute("component", component);
        comp.setAnnotazioni(component.getAnnotazioni());
        componentRepository.save(comp);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/add/worksheet/{id}")
    public String addWorkSheet(@PathVariable("id") int id, @Valid @ModelAttribute("worksheet") WorkSheet worksheet, Model model, Model modelWorks) {
        LocalDate localDate = LocalDate.now();
        String data = localDate.toString();
        Work work = new Work();
        componentRepository.findById(id).ifPresent(o -> modelWorks.addAttribute("component", o));
        model.addAttribute("worksheet", worksheet);
        //workT = work;
        worksheet.setData(data);
        worksheet.setFkcomp(id);
        worksheetRepository.save(worksheet);

        return "redirect:/component/dashboard/{id}";
    }
    @PostMapping("/worksheet/update/ore/{id}/{idws}")
    public String updateOre(@PathVariable("id") int id,@PathVariable("idws") int idws, @Valid @ModelAttribute("worksheet") WorkSheet worksheet, Model model) {
        WorkSheet ws = worksheetRepository.findById(idws).get();
       
        model.addAttribute("worksheet", worksheet);
        ws.setOrainizio(worksheet.getOrainizio());
        ws.setOrafine(worksheet.getOrafine());
        worksheetRepository.saveAndFlush(ws);

        return "redirect:/component/dashboard/{id}";
    }
    }


