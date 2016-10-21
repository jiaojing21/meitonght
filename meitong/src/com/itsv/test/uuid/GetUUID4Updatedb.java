package com.itsv.test.uuid;

import com.itsv.platform.UuidGenerator;

public class GetUUID4Updatedb {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("gbp_rolemenu");
		for(int i=0;i<16;i++)
			//System.out.println((1+i));
			System.out.println("'"+UuidGenerator.getUUID()+"'");
			System.out.println();
	}

}
