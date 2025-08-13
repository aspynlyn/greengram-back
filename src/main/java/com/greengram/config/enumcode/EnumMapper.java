package com.greengram.config.enumcode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 내부적으로 관리한느 공통코드를 요청을 통해 확인하고 싶을 때 사용
public class EnumMapper {
  private Map<String, List<EnumMapperValue>> factory = new LinkedHashMap<>();
  // String은 key 타입, List<EnumMapperValue>는 value 타입

  // e는 EnumMapperValue를 상속받은 어떤 타입이든 전달 될 수 있음
  public void put(String key, Class<? extends EnumMapperValue> e) {
    factory.put(key, toEnumValues(e));
  }


  private List<EnumMapperValue> toEnumValues(Class<? extends EnumMapperValue> e) {
    e.getEnumConstants();//특정 enum 타입이 갖고 있는 모든 값을 출력시키는 기능은 class의 getEnumConstants() 메소드를 사용
    return null;
  }
}
