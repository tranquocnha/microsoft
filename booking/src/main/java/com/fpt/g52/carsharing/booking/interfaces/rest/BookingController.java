package com.fpt.g52.carsharing.booking.interfaces.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.g52.carsharing.booking.application.internal.commandservices.BookingCommandService;
import com.fpt.g52.carsharing.booking.application.internal.outboundservices.acl.UserService;
import com.fpt.g52.carsharing.booking.application.internal.outboundservices.acl.VehicleService;
import com.fpt.g52.carsharing.booking.application.internal.queryservices.BookingQueryService;
import com.fpt.g52.carsharing.booking.domain.exceptions.ResourceInvalidException;
import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;
import com.fpt.g52.carsharing.booking.domain.model.commands.BookingCommand;
import com.fpt.g52.carsharing.booking.domain.model.entities.User;
import com.fpt.g52.carsharing.booking.domain.model.entities.Vehicle;
import com.fpt.g52.carsharing.booking.interfaces.rest.dto.request.BookingRequest;
import com.fpt.g52.carsharing.booking.interfaces.rest.dto.response.BookingResponse;
import com.fpt.g52.carsharing.booking.interfaces.rest.dto.response.MessageResponse;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingCommandService commandService;

    private final BookingQueryService queryService;
    
    private final VehicleService vehicleService;
    
    private final UserService userService;
    

    public BookingController(
            BookingCommandService commandService,
            BookingQueryService queryService,
            VehicleService vehicleService,
            UserService userService
    ) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> book(
            @RequestBody @Valid BookingRequest request
    ) throws Exception {
        
        // get vehicle info
        Vehicle vehicle = vehicleService.getVehicleById(request.vehicleId());
        
        User userLogin = getUserInfo(null);

        BookingCommand command = BookingCommand.builder()
                .userId(userLogin.getId())
                .vehicleId(request.vehicleId())
                .bookingFrom(request.from())
                .bookingTo(request.to())
                .bookingTime(System.currentTimeMillis())
                .vehiclePrice(vehicle.getPricing())
                .vehicleName(vehicle.getName())
                .build();
        
        commandService.book(command);
        return ResponseEntity.ok(new MessageResponse("success!"));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> search(
            @RequestParam("query") String query,
            Pageable pageable
    ) {
        // get user info from token
        User userLogin = getUserInfo(null);

        Page<Booking> page = queryService.search(query, pageable);
        
        Page<BookingResponse> pageWithFilteredData = new PageImpl<Booking>(page.stream().filter(item -> item.getAccount().getId().equals(userLogin.getId()))
                .collect(Collectors.toList()), page.getPageable(), page.getTotalElements()).map(BookingResponse::new);
        
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Size", List.of(String.valueOf(pageWithFilteredData.getSize())));
        headers.put("Total-Elements", List.of(String.valueOf(pageWithFilteredData.getTotalElements())));
        headers.put("Total-Pages", List.of(String.valueOf(pageWithFilteredData.getTotalPages())));

        return new ResponseEntity<>(pageWithFilteredData.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> findById(
            @PathVariable("id") String id
    ) {
        getUserInfo(null);
        Booking booking = queryService.findById(id);
        return ResponseEntity.ok(new BookingResponse(booking));
    }

    @PutMapping("/{id}/receive")
    public ResponseEntity<MessageResponse> receive(
            @PathVariable("id") String id
    ) {
        getUserInfo(null);
        commandService.receive(id);
        return ResponseEntity.ok(new MessageResponse("success!"));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<MessageResponse> complete(
            @PathVariable("id") String id
    ) {
        getUserInfo(null);
        commandService.complete(id);
        return ResponseEntity.ok(new MessageResponse("success!"));
    }
    
    @PutMapping("/{id}/paycomplete")
    public ResponseEntity<MessageResponse> payComplete(
            @PathVariable("id") String id
    ) {
        getUserInfo(null);
        commandService.payComplete(id);
        return ResponseEntity.ok(new MessageResponse("success!"));
    }
    
    @GetMapping("/vehicle/{id}")
    public ResponseEntity<List<BookingResponse>> findByVehicleId(
            @PathVariable("id") String id,Pageable pageable
    ) {
        User userLogin = getUserInfo(null);
        Page<Booking> page = queryService.findByVehicleId(id, pageable);
        
        Page<BookingResponse> pageWithFilteredData = new PageImpl<Booking>(page.stream().filter(item -> item.getAccount().getId().equals(userLogin.getId()))
                .collect(Collectors.toList()), page.getPageable(), page.getTotalElements()).map(BookingResponse::new);
        
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Size", List.of(String.valueOf(pageWithFilteredData.getSize())));
        headers.put("Total-Elements", List.of(String.valueOf(pageWithFilteredData.getTotalElements())));
        headers.put("Total-Pages", List.of(String.valueOf(pageWithFilteredData.getTotalPages())));

        return new ResponseEntity<>(pageWithFilteredData.getContent(), headers, HttpStatus.OK);
    }
    
    private User getUserInfo(String token) {
        User userLogin = userService.getUserByid(null);
        
        if (userLogin == null) {
            throw new ResourceInvalidException("session expired!");
        }
        
        return userLogin;
    }
}
