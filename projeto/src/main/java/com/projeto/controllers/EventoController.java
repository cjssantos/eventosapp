package com.projeto.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.models.Convidado;
import com.projeto.models.Evento;
import com.projeto.repository.ConvidadoRepository;
import com.projeto.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	
	@GetMapping("/cadastrarEvento")
	//@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public ModelAndView form() {
		
		Evento evento = new Evento();
		ModelAndView mv = new ModelAndView("evento/formEvento");
		mv.addObject("evento", evento);
		
		return mv;
		
	}
	
	@PostMapping("/cadastrarEvento")
	//@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {
		
		er.save(evento);
		return "redirect:/cadastrarEvento";
	}
	
	
	@GetMapping("eventos/alterar/{codigo}")
	//@RequestMapping(value = "eventos/alterar/{codigo}", method = RequestMethod.GET)
	public ModelAndView alterarEvento(@PathVariable("codigo") long codigo) {
		
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/formEvento");
		mv.addObject("evento", evento);
		
		return mv;
	}
	
	@PostMapping("eventos/alterar/{codigo}")
	public String alterarEventoPost(Evento evento) {
		
		form(evento);
		
		return "redirect:/eventos";
	}
	
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		
		ModelAndView mv = new ModelAndView("evento/listaEventos");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		
		return mv;
	}
	
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		
		return "redirect:/eventos";
	}
	
	
	@GetMapping("eventos/{codigo}")
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		
		Iterable<Convidado> convidado = cr.findByEvento(evento);
		mv.addObject("convidado", convidado);
		
		return mv;
	}
	
	@PostMapping("eventos/{codigo}")
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos ! ! !");
			return "redirect:{codigo}";
		}
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		
		attributes.addFlashAttribute("mensagem", "Convidado Inserido com Sucesso ! ! !");
		return "redirect:{codigo}";
	}
	
	
	@RequestMapping("eventos/deletarConvidado")
	public String deletarConvidado(long codigo) {
		
		Convidado convidado = cr.findById(codigo);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoEvento = evento.getCodigo();
		
		return "redirect:/eventos/"+codigoEvento;
	}
	
}
