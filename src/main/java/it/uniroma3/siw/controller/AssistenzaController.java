package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/assistenza-clienti")
public class AssistenzaController {
	
	 @GetMapping
	    public String privacy() {
	        return "assistenza/view";
	    }

    @GetMapping("/contatti")
    public String contatti(Model model) {
        model.addAttribute("email", "support@intimilunna.it");
        model.addAttribute("telefono", "+39 123 456 7890");
        return "assistenza/contatti";
    }

    @GetMapping("/faq")
    public String faq() {
        return "assistenza/faq";
    }

    @GetMapping("/traccia-ordine")
    public String tracciaOrdine() {
        return "assistenza/traccia-ordine";
    }

    @PostMapping("/traccia-ordine")
    public String tracciaOrdine(@RequestParam String codice,
                                @RequestParam String cognome,
                                RedirectAttributes redirectAttributes) {

        return "redirect:/assistenza-clienti/traccia-ordine";
    }
}