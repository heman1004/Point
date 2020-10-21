package yotakbae;

import yotakbae.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Iterator;
import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired
    PointAggRepository pointAggRepository;
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDelivered_GetPointPol(@Payload Delivered delivered){

        if(delivered.isMe()){
            //LJK

            Iterator<PointAgg> iterator = pointAggRepository.findAll().iterator();
            while(iterator.hasNext()){
                PointAgg pointTmp = iterator.next();
                if(pointTmp.getMemberId() == delivered.getMemberId() && delivered.getStatus()=="Finish" ){
                    Optional<PointAgg> PointOptional = pointAggRepository.findById(pointTmp.getId());
                    PointAgg Pointagg = PointOptional.get();
                    Pointagg.setPoint(Pointagg.getPoint()+1000);
                    pointAggRepository.save(Pointagg);
                }
            }
            //LJK

        }
    }

}
