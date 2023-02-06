package map.busan.animal.hospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import map.busan.animal.hospital.vo.ItemVO;
import map.busan.animal.hospital.vo.ResultVO;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
public class MainController {

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model){
        log.info("index Controller...");
        String apiURL = "http://apis.data.go.kr/6260000/BusanAnimalHospService/getTblAnimalHospital";
        String serviceKey = "QGwFhZ%2BYitChVGNNYxdK1NdjAUN6FekmszZlqwTkG6BVQC62%2F6HyN8kFWvPGJTbEKWdJ6lFXQwffzJcZDDhVKg%3D%3D";
        String resultType = "json";
        String pageNo = "1";
        String numOfRows = "1000";

        URI uri = UriComponentsBuilder
                .fromUriString(apiURL)
                .queryParam("serviceKey", serviceKey)
                .queryParam("resultType", resultType)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .encode()
                .build(true)
                .toUri();

        RequestEntity<Void> req = RequestEntity.get(uri).build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        // JSON 문자열
        String jsonData = result.getBody();

        // JSON 파싱
        ObjectMapper om = new ObjectMapper();
        try{
            ResultVO resultVO = om.readValue(jsonData, ResultVO.class);
            List<ItemVO> items = resultVO.getGetTblAnimalHospital().getBody().getItems().getItem();
            System.out.println(items);

            model.addAttribute("items", items);
        }catch(JsonMappingException e){
            e.printStackTrace();
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }


        return "index";
    }
}
