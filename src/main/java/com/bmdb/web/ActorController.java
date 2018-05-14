package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmdb.business.actor.Actor;
import com.bmdb.business.actor.ActorRepository;
import com.bmdb.util.BMDBMaintenanceReturn;

@CrossOrigin
@Controller    
@RequestMapping(path="/Actors")
public class ActorController extends BaseController{
	@Autowired 
	private ActorRepository actorRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<Actor> getAllActors() {
		// This returns a JSON or XML with the actors
		return actorRepository.findAll();
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody List<Actor> getActor(@RequestParam int id) {
		// This returns a JSON or XML with one specific actor
		Optional<Actor> u = actorRepository.findById(id);
		return getReturnArray(u);
	}
	
	@PostMapping(path="/Add") 
	public @ResponseBody BMDBMaintenanceReturn addNewActor (@RequestBody Actor actor) {
		try {
			actorRepository.save(actor);
			return BMDBMaintenanceReturn.getMaintReturn(actor);
		}
		catch (DataIntegrityViolationException dive) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return BMDBMaintenanceReturn.getMaintReturnError(actor, e.getMessage());
		}
	}
	
	@GetMapping(path="/Remove") // Map ONLY GET Requests
	public @ResponseBody BMDBMaintenanceReturn deleteActor (@RequestParam int id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		Optional<Actor> actor = actorRepository.findById(id);
		try {
			actorRepository.delete(actor.get());
			return BMDBMaintenanceReturn.getMaintReturn(actor.get());
		}
		catch (DataIntegrityViolationException dive) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, e.toString());
		}
		
	}

	@PostMapping(path="/Change") 
	public @ResponseBody BMDBMaintenanceReturn updateActor (@RequestBody Actor actor) {
		try {
			actorRepository.save(actor);
			return BMDBMaintenanceReturn.getMaintReturn(actor);
		}
		catch (DataIntegrityViolationException dive) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return BMDBMaintenanceReturn.getMaintReturnError(actor, e.toString());
		}
		
	}
	
//	@GetMapping(path = "/Authenticate")
//	public @ResponseBody List<Actor> authenticate(@RequestParam String uname, @RequestParam String pwd) {
//		Actor actor = new Actor();
//		actor = actorRepository.findByActorNameAndPassword(uname, pwd);
//		return BaseController.getReturnArray(actor);
//	}
	
}