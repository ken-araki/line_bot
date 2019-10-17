package com.linebot.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDelay {
	private String name;
	private String company;
	private String lastupdate_gmt;
	private String source;
}
