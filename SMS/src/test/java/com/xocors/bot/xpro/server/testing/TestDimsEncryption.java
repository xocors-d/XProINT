package com.xocors.bot.xpro.server.testing;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.chrono.ISOChronology;

public class TestDimsEncryption {


	private static final String[] E ={"%20", ";;;", "%3B", "%2C", "und", "fin", "ed;", "%28", "%29", "%3A", "/53", "ike", "Web", "0;", ".0", "e;", "on", "il", "ck", "01", "in", "Mo", "fa", "00", "32", "la", ".1", "ri", "it", "%u", "le"};
	private static final Map<Integer, Integer[]> Y;
	static
	{
		Y = new HashMap<Integer, Integer[]>();
		Y.put(0, new Integer[]{16,44928});
		Y.put(1, new Integer[]{4,15});
		Y.put(10, new Integer[]{7,63});
		Y.put(100, new Integer[]{7,78});
		Y.put(101, new Integer[]{5,28});
		Y.put(102, new Integer[]{9,319});
		Y.put(103, new Integer[]{9,423});
		Y.put(104, new Integer[]{7,111});
		Y.put(105, new Integer[]{6,54});
		Y.put(106, new Integer[]{7,30});
		Y.put(107, new Integer[]{13,5617});
		Y.put(108, new Integer[]{6,17});
		Y.put(109, new Integer[]{7,106});
		Y.put(11, new Integer[]{8,181});
		Y.put(110, new Integer[]{8,239});
		Y.put(111, new Integer[]{6,49});
		Y.put(112, new Integer[]{6,40});
		Y.put(113, new Integer[]{11,1405});
		Y.put(114, new Integer[]{6,27});
		Y.put(115, new Integer[]{6,25});
		Y.put(116, new Integer[]{6,32});
		Y.put(117, new Integer[]{6,41});
		Y.put(118, new Integer[]{8,173});
		Y.put(119, new Integer[]{7,59});
		Y.put(12, new Integer[]{8,170});
		Y.put(120, new Integer[]{8,65});
		Y.put(121, new Integer[]{8,125});
		Y.put(122, new Integer[]{8,95});
		Y.put(13, new Integer[]{8,117});
		Y.put(14, new Integer[]{6,24});
		Y.put(15, new Integer[]{7,91});
		Y.put(16, new Integer[]{7,88});
		Y.put(17, new Integer[]{7,73});
		Y.put(18, new Integer[]{7,69});
		Y.put(19, new Integer[]{7,57});
		Y.put(2, new Integer[]{5,3});
		Y.put(20, new Integer[]{7,16});
		Y.put(21, new Integer[]{7,31});
		Y.put(22, new Integer[]{7,33});
		Y.put(23, new Integer[]{7,18});
		Y.put(24, new Integer[]{7,45});
		Y.put(25, new Integer[]{8,232});
		Y.put(26, new Integer[]{8,210});
		Y.put(27, new Integer[]{8,179});
		Y.put(28, new Integer[]{8,178});
		Y.put(29, new Integer[]{8,174});
		Y.put(3, new Integer[]{6,4});
		Y.put(30, new Integer[]{8,172});
		Y.put(31, new Integer[]{8,158});
		Y.put(37, new Integer[]{7,44});
		Y.put(4, new Integer[]{7,110});
		Y.put(42, new Integer[]{15,22465});
		Y.put(43, new Integer[]{9,45});
		Y.put(45, new Integer[]{7,96});
		Y.put(46, new Integer[]{6,51});
		Y.put(47, new Integer[]{6,14});
		Y.put(48, new Integer[]{6,33});
		Y.put(49, new Integer[]{5,5});
		Y.put(5, new Integer[]{7,107});
		Y.put(50, new Integer[]{5,10});
		Y.put(51, new Integer[]{5,9});
		Y.put(52, new Integer[]{5,1});
		Y.put(53, new Integer[]{5,6});
		Y.put(54, new Integer[]{5,0});
		Y.put(55, new Integer[]{6,37});
		Y.put(56, new Integer[]{7,117});
		Y.put(57, new Integer[]{7,118});
		Y.put(59, new Integer[]{5,23});
		Y.put(6, new Integer[]{7,104});
		Y.put(64, new Integer[]{16,44929});
		Y.put(65, new Integer[]{7,68});
		Y.put(66, new Integer[]{8,64});
		Y.put(67, new Integer[]{7,56});
		Y.put(68, new Integer[]{9,318});
		Y.put(69, new Integer[]{7,60});
		Y.put(7, new Integer[]{7,97});
		Y.put(70, new Integer[]{7,10});
		Y.put(71, new Integer[]{8,233});
		Y.put(72, new Integer[]{8,180});
		Y.put(73, new Integer[]{8,23});
		Y.put(74, new Integer[]{8,238});
		Y.put(75, new Integer[]{7,17});
		Y.put(76, new Integer[]{7,46});
		Y.put(77, new Integer[]{7,70});
		Y.put(78, new Integer[]{8,124});
		Y.put(79, new Integer[]{8,116});
		Y.put(8, new Integer[]{7,72});
		Y.put(80, new Integer[]{7,61});
		Y.put(81, new Integer[]{9,350});
		Y.put(82, new Integer[]{9,422});
		Y.put(83, new Integer[]{6,26});
		Y.put(84, new Integer[]{6,38});
		Y.put(85, new Integer[]{8,38});
		Y.put(86, new Integer[]{8,39});
		Y.put(87, new Integer[]{9,44});
		Y.put(88, new Integer[]{10,703});
		Y.put(89, new Integer[]{12,2809});
		Y.put(9, new Integer[]{7,71});
		Y.put(90, new Integer[]{14,11233});
		Y.put(95, new Integer[]{8,94});
		Y.put(97, new Integer[]{6,50});
		Y.put(98, new Integer[]{8,171});
		Y.put(99, new Integer[]{7,84});

		/*
        Y.put(0, new String[]{"16","44928"});
        Y.put(1, new String[]{"4","15"});
        Y.put(10, new String[]{"7","63"});
        Y.put(100, new String[]{"7","78"});
        Y.put(101, new String[]{"5","28"});
        Y.put(102, new String[]{"9","319"});
        Y.put(103, new String[]{"9","423"});
        Y.put(104, new String[]{"7","111"});
        Y.put(105, new String[]{"6","54"});
        Y.put(106, new String[]{"7","30"});
        Y.put(107, new String[]{"13","5617"});
        Y.put(108, new String[]{"6","17"});
        Y.put(109, new String[]{"7","106"});
        Y.put(11, new String[]{"8","181"});
        Y.put(110, new String[]{"8","239"});
        Y.put(111, new String[]{"6","49"});
        Y.put(112, new String[]{"6","40"});
        Y.put(113, new String[]{"11","1405"});
        Y.put(114, new String[]{"6","27"});
        Y.put(115, new String[]{"6","25"});
        Y.put(116, new String[]{"6","32"});
        Y.put(117, new String[]{"6","41"});
        Y.put(118, new String[]{"8","173"});
        Y.put(119, new String[]{"7","59"});
        Y.put(12, new String[]{"8","170"});
        Y.put(120, new String[]{"8","65"});
        Y.put(121, new String[]{"8","125"});
        Y.put(122, new String[]{"8","95"});
        Y.put(13, new String[]{"8","117"});
        Y.put(14, new String[]{"6","24"});
        Y.put(15, new String[]{"7","91"});
        Y.put(16, new String[]{"7","88"});
        Y.put(17, new String[]{"7","73"});
        Y.put(18, new String[]{"7","69"});
        Y.put(19, new String[]{"7","57"});
        Y.put(2, new String[]{"5","3"});
        Y.put(20, new String[]{"7","16"});
        Y.put(21, new String[]{"7","31"});
        Y.put(22, new String[]{"7","33"});
        Y.put(23, new String[]{"7","18"});
        Y.put(24, new String[]{"7","45"});
        Y.put(25, new String[]{"8","232"});
        Y.put(26, new String[]{"8","210"});
        Y.put(27, new String[]{"8","179"});
        Y.put(28, new String[]{"8","178"});
        Y.put(29, new String[]{"8","174"});
        Y.put(3, new String[]{"6","4"});
        Y.put(30, new String[]{"8","172"});
        Y.put(31, new String[]{"8","158"});
        Y.put(37, new String[]{"7","44"});
        Y.put(4, new String[]{"7","110"});
        Y.put(42, new String[]{"15","22465"});
        Y.put(43, new String[]{"9","45"});
        Y.put(45, new String[]{"7","96"});
        Y.put(46, new String[]{"6","51"});
        Y.put(47, new String[]{"6","14"});
        Y.put(48, new String[]{"6","33"});
        Y.put(49, new String[]{"5","5"});
        Y.put(5, new String[]{"7","107"});
        Y.put(50, new String[]{"5","10"});
        Y.put(51, new String[]{"5","9"});
        Y.put(52, new String[]{"5","1"});
        Y.put(53, new String[]{"5","6"});
        Y.put(54, new String[]{"5","0"});
        Y.put(55, new String[]{"6","37"});
        Y.put(56, new String[]{"7","117"});
        Y.put(57, new String[]{"7","118"});
        Y.put(59, new String[]{"5","23"});
        Y.put(6, new String[]{"7","104"});
        Y.put(64, new String[]{"16","44929"});
        Y.put(65, new String[]{"7","68"});
        Y.put(66, new String[]{"8","64"});
        Y.put(67, new String[]{"7","56"});
        Y.put(68, new String[]{"9","318"});
        Y.put(69, new String[]{"7","60"});
        Y.put(7, new String[]{"7","97"});
        Y.put(70, new String[]{"7","10"});
        Y.put(71, new String[]{"8","233"});
        Y.put(72, new String[]{"8","180"});
        Y.put(73, new String[]{"8","23"});
        Y.put(74, new String[]{"8","238"});
        Y.put(75, new String[]{"7","17"});
        Y.put(76, new String[]{"7","46"});
        Y.put(77, new String[]{"7","70"});
        Y.put(78, new String[]{"8","124"});
        Y.put(79, new String[]{"8","116"});
        Y.put(8, new String[]{"7","72"});
        Y.put(80, new String[]{"7","61"});
        Y.put(81, new String[]{"9","350"});
        Y.put(82, new String[]{"9","422"});
        Y.put(83, new String[]{"6","26"});
        Y.put(84, new String[]{"6","38"});
        Y.put(85, new String[]{"8","38"});
        Y.put(86, new String[]{"8","39"});
        Y.put(87, new String[]{"9","44"});
        Y.put(88, new String[]{"10","703"});
        Y.put(89, new String[]{"12","2809"});
        Y.put(9, new String[]{"7","71"});
        Y.put(90, new String[]{"14","11233"});
        Y.put(95, new String[]{"8","94"});
        Y.put(97, new String[]{"6","50"});
        Y.put(98, new String[]{"8","171"});
        Y.put(99, new String[]{"7","84"});
		 */
	}
	private static final String S = ".0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";

	private int p = new Date(2005, 0, 15).getTimezoneOffset();
	private int q = new Date(2005, 6, 15).getTimezoneOffset();

	String g_b ="";
	String g_output ="";
	int g_e=0;
	int g_f=0;
	private String origin_input;

	public TestDimsEncryption() {

	}

	public static void main(String[] args) {
		new TestDimsEncryption().process();
	}

	public void process(){
		/*
		String input = "TF1;020;;;;;;;;;;;;;;;;;;;;;;Mozilla;Netscape;5.0%20%28Windows%20NT%2010.0%3B%20Win64%3B%20x64%29%20AppleWebKit/537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome/45.0.2454.46%20Safari/537.36;20030107;undefined;true;;true;Win32;undefined;Mozilla/5.0%20%28Windows%20NT%2010.0%3B%20Win64%3B%20x64%29%20AppleWebKit/537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome/45.0.2454.46%20Safari/537.36;zh-CN;GBK;;undefined;undefined;undefined;undefined;false;false;1440571081907;8;2005/6/7%20%u4E0B%u53489%3A33%3A44;1920;1080;;19.0;;;;;7387;-480;-480;2015/8/26%20%u4E0B%u53482%3A37%3A54;24;1920;1040;0;0;;;;;;Shockwave%20Flash%7CShockwave%20Flash%2019.0%20r0;;;;;;;;;;;;;20;;;;;;;;;;;;;;;5.6.1-0;;";

		System.out.println("Result:\n"+input);
		 */
		//decryption("");
		try {
			System.out.println(funcA(null));
			System.out.println(origin_input);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String funcW(String input){
		//TestData input = "TF1;020;;;;;;;;;;;;;;;;;;;;;;Mozilla;Netscape;5.0%20%28Windows%20NT%2010.0%3B%20Win64%3B%20x64%29%20AppleWebKit/537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome/45.0.2454.46%20Safari/537.36;20030107;undefined;true;;true;Win32;undefined;Mozilla/5.0%20%28Windows%20NT%2010.0%3B%20Win64%3B%20x64%29%20AppleWebKit/537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome/45.0.2454.46%20Safari/537.36;en-US;windows-1252;;undefined;undefined;undefined;undefined;false;false;1440611265617;8;6/7/2005%2C%209%3A33%3A44%20PM;1920;1080;;19.0;;;;;10;-480;-480;8/27/2015%2C%201%3A47%3A45%20AM;24;1920;1040;0;0;;;;;;Shockwave%20Flash%7CShockwave%20Flash%2019.0%20r0;;;;;;;;;;;;;20;;;;;;;;;;;;;;;5.6.1-0;;";
		                    
		origin_input = input;
		for(int i=0; i<E.length;i++){
			//System.out.println("Replacing "+E[i]+" with "+ String.valueOf((char)(i+1)));

			int pos;
			do{
				pos = input.indexOf(E[i]);
				if(pos!=-1){
					input = input.substring(0, pos)+String.valueOf((char)(i+1))+input.substring(pos+E[i].length());
				}
			}while(pos!=-1);

			//input = input.replaceAll(E[i], String.valueOf((char)i));
			//System.out.println(input);
		}

		String g_output = funcG(input);
		if(g_output==null) {
			return input;
		}else{
			int b = 65535;
			for (int e = 0; e < origin_input.length(); e++) {
				b = (b >>> 8 | b << 8) & 65535;
				b ^= (int)origin_input.charAt(e) & 255;
				b ^= (b & 255) >> 4;
				b ^= b << 12 & 65535;
				b ^= (b & 255) << 5 & 65535;
			}
			b &= 65535;
			String a = "";
			a += S.charAt(b >>> 12);
			a += S.charAt(b >>> 6 & 63);
			a += S.charAt(b & 63);
			g_output += a;
			return g_output;
		}
		//return null;
	}


	/*
	function G(a) {
        function c(h) {
            e = e << h[0] | h[1];
            for (f += h[0]; f >= 6;) {
                h = e >> f - 6 & 63;
                b += s.substring(h,
                    h + 1);
                f -= 6;
                e ^= h << f
            }
        }
        var b = "",
            e = 0,
            f = 0;
        c([6, (a.length & 7) << 3 | 0]);
        c([6, a.length & 56 | 1]);
        for (var d = 0; d < a.length; d++) {
            if (y[a.charCodeAt(d)] == undefined) return;
            c(y[a.charCodeAt(d)])
        }
        c(y[0]);
        f > 0 && c([6 - f, 0]);
        return b
    }
	 */

	public String funcG(String input){
		g_output = "";
		//int a = input.charAt(0);
		//Integer[] aa = Y.get((int)input.charAt(0));
		funcC(new Integer[]{6, (input.length() & 7)<<3|0});		
		funcC(new Integer[]{6, input.length() & 56|1});
		for (int d = 0; d < input.length(); d++) {
			if (Y.get((int)input.charAt(d)) == null) return null;
			funcC(Y.get((int)input.charAt(d)));
		}
		funcC(Y.get(0));

		if(g_f>0){
			funcC(new Integer[]{6-g_f,0});
		}
		//g_f > 0 && funcC(new Integer[]{6-g_f, 0});
		//return b

		return g_b;
	}

	public void funcC(Integer[] h){
		int pos=0;
		g_e = g_e << h[0]|h[1];
		for (g_f += h[0]; g_f >= 6;) {
			pos = g_e >> g_f - 6 & 63;
		g_b += S.substring(pos, pos + 1);
		g_f -= 6;
		g_e ^= pos << g_f;
		}
	}

	/*
	public void decryption(String input){
		input = "a44j1e3NlY5BSo9z4ofjb75PaK4Vpjt.gEngMQEjZr_WhXTA2s.XTVV26y8GGEDd5ihORoVyFGh8cmvSuCKzIlnY6xljQlpRD1Prac8VtW0vLG9mhORoVidPZW2AUMnGWVQdgMVQdg1kzoMpwoNJ9z4oYYLzZ1kzDlSgyyITL5q8sgEV18u1.BUs_43wuZPup_nH2t05oaYAhrcpMxE6DBUr5xj6Kks8fTPVa2a36hO3f9p_nH1zDz.ICMSbtqAhb.KG_MjftckuyPBDjaY2ftckZZLQ084akJ7NF4JJV5KKyhk6Hb9LarUqUdHz16rgPtTma1kxLA_vLlrjp_AU.6elV2pNub9_DJFCixIwfwBEl7pp0iMgdVg7L57GYPrsiMTKQnlLZnjLHi5hyA_r_LwwKdBvzAqhyr1BNlrK1BNlYCa1nkBMfs.";
		int pos=0;
		int g_f=0;
		int g_e=0;
		
		for(int i = input.length()-1;i>=0;i--){
			g_f+=6;
			pos = S.indexOf(input.charAt(i));
			g_e = pos << g_f+6&63;
			//pos << 
			
			
			
		}
		
		
	}
	*/
	
	/*
	 function A(a) {
        var c = new Date,
            b = new Date,
            e = [t("TF1"), t("020"), function() {
                    return ScriptEngineMajorVersion()
                , function() {
                    return ScriptEngineMinorVersion()
                , function() {
                    return ScriptEngineBuildVersion()
                , function() {
                    funcI("{7790769C-0471-11D2-AF11-00C04FA35D02}")
                , function() {
                    funcI("{89820200-ECBD-11CF-8B85-00AA005B4340}")
                , function() {
                    funcI("{283807B5-2C60-11D0-A31D-00AA00B92C03}")
                ,
                function() {
                    funcI("{4F216970-C90C-11D1-B5C7-0000F8051515}")
                ,
                function() {
                    funcI("{44BBA848-CC51-11CF-AAFA-00AA00B6015C}")
                ,
                function() {
                    funcI("{9381D8F2-0288-11D0-9501-00AA00B911A5}")
                ,
                function() {
                    funcI("{4F216970-C90C-11D1-B5C7-0000F8051515}")
                ,
                function() {
                    funcI("{5A8D6EE0-3E18-11D0-821E-444553540000}")
                ,
                function() {
                    funcI("{89820200-ECBD-11CF-8B85-00AA005B4383}")
                ,
                function() {
                    funcI("{08B0E5C0-4FCB-11CF-AAA5-00401C608555}")
                ,
                function() {
                    funcI("{45EA75A0-A269-11D1-B5BF-0000F8051515}")
                ,
                function() {
                    funcI("{DE5AED00-A4BF-11D1-9948-00C04F98BBC9}")
                ,
                function() {
                    funcI("{22D6F312-B0F6-11D0-94AB-0080C74C7E95}")
                ,
                function() {
                    funcI("{44BBA842-CC51-11CF-AAFA-00AA00B6015B}")
                ,
                function() {
                    funcI("{3AF36230-A269-11D1-B5BF-0000F8051515}")
                ,
                function() {
                    funcI("{44BBA840-CC51-11CF-AAFA-00AA00B6015C}")
                ,
                function() {
                    funcI("{CC2A9BA0-3BDD-11D0-821E-444553540000}")
                ,
                function() {
                    funcI("{08B0E5C0-4FCB-11CF-AAA5-00401C608500}")
                ,
                function() {
                    return eval("navigator.appCodeName")
                ,
                function() {
                    return eval("navigator.appName")
                ,
                function() {
                    return eval("navigator.appVersion")
                ,
                function() {
                    return u(["navigator.productSub", "navigator.appMinorVersion"])
                ,
                function() {
                    return eval("navigator.browserLanguage")
                ,
                function() {
                    return eval("navigator.cookieEnabled")
                ,
                function() {
                    return u(["navigator.oscpu", "navigator.cpuClass"])
                ,
                function() {
                    return eval("navigator.onLine")
                ,
                function() {
                    return eval("navigator.platform")
                ,
                function() {
                    return eval("navigator.systemLanguage")
                ,
                function() {
                    return eval("navigator.userAgent")
                ,
                function() {
                    return u(["navigator.language",
                        "navigator.userLanguage"
                    ])
                ,
                function() {
                    return eval("document.defaultCharset")
                ,
                function() {
                    return eval("document.domain")
                ,
                function() {
                    return eval("screen.deviceXDPI")
                ,
                function() {
                    return eval("screen.deviceYDPI")
                ,
                function() {
                    return eval("screen.fontSmoothingEnabled")
                ,
                function() {
                    return eval("screen.updateInterval")
                ,
                function() {
                    return Math.abs(p - q) !== 0
                ,
                function() {
                    return C(c)
                ,
                function() {
                    return "@UTC@"
                ,
                function() {
                    var j = 0;
                    j = 0;
                    if (C(c)) j = Math.abs(p - q);
                    return j = -(c.getTimezoneOffset() + j) / 60
                ,
                function() {
                    return (new Date(2005, 5, 7, 21, 33, 44, 888)).toLocaleString()
                ,
                function() {
                    return eval("screen.width")
                ,
                function() {
                    return eval("screen.height")
                ,
                function() {
                    return n.Acrobat
                ,
                function() {
                    return n.Flash
                ,
                function() {
                    return n.QuickTime
                ,
                function() {
                    return n["Java Plug-in"]
                ,
                function() {
                    return n.Director
                ,
                function() {
                    return n.Office
                ,
                function() {
                    return "@CT@"
                ,
                function() {
                    return p
                ,
                function() {
                    return q
                ,
                function() {
                    return c.toLocaleString()
                ,
                function() {
                    return eval("screen.colorDepth")
                ,
                function() {
                    return eval("window.screen.availWidth")
                ,
                function() {
                    return eval("window.screen.availHeight")
                ,
                function() {
                    return eval("window.screen.availLeft")
                ,
                function() {
                    return eval("window.screen.availTop")
                ,
                function() {
                    return g("Acrobat")
                ,
                function() {
                    return g("Adobe SVG")
                ,
                function() {
                    return g("Authorware")
                ,
                function() {
                    return g("Citrix ICA")
                ,
                function() {
                    return g("Director")
                ,
                function() {
                    return g("Flash")
                ,
                function() {
                    return g("MapGuide")
                ,
                function() {
                    return g("MetaStream")
                ,
                function() {
                    return g("PDFViewer")
                ,
                function() {
                    return g("QuickTime")
                ,
                function() {
                    return g("RealOne")
                ,
                function() {
                    return g("RealPlayer Enterprise")
                ,
                function() {
                    return g("RealPlayer Plugin")
                ,
                function() {
                    return g("Seagate Software Report")
                ,
                function() {
                    return g("Silverlight")
                ,
                function() {
                    return g("Windows Media")
                ,
                function() {
                    return g("iPIX")
                ,
                function() {
                    return g("nppdf.so")
                ,
                function() {
                    var j = document.createElement("span");
                    j.innerHTML = "&nbsp;";
                    j.style.position = "absolute";
                    j.style.left = "-9999px";
                    document.body.appendChild(j);
                    var r = j.offsetHeight;
                    document.body.removeChild(j);
                    return r
                ,
                k(), k(), k(), k(), k(),
                k(), k(), k(), k(), k(), k(), k(), k(), k(),
                function() {
                    return "5.6.1-0"
                ,
                k()
            ];
        F();
        for (var f = "", d = 0; d < e.length; d++) {
            if (a) {
                f += v(e[d].toString(), '"', "'", true);
                f += "="
            }
            var h;
            try {
                h = e[d](this)
            } catch (l) {
                h = ""
            }
            f += a ? h : escape(h);
            f += ";";
            if (a) f += "\\n"
        }
        f = v(f, escape("@UTC@"), (new Date).getTime());
        f = v(f, escape("@CT@"), (new Date).getTime() - b.getTime());
        return D && w ? w(f) : f
    }
	 */

	public String funcA(String a) throws UnsupportedEncodingException{
		//Date c,b = new Date();
		long b = System.currentTimeMillis();
		String f = "";
		String[] e =new String[]{
				"TF1",
				"020",
				"",//ScriptEngineMajorVersion()
				"",//ScriptEngineMinorVersion()
				"",//ScriptEngineBuildVersion()
				funcI("{7790769C-0471-11D2-AF11-00C04FA35D02}"),
				funcI("{89820200-ECBD-11CF-8B85-00AA005B4340}"),
				funcI("{283807B5-2C60-11D0-A31D-00AA00B92C03}"),
				funcI("{4F216970-C90C-11D1-B5C7-0000F8051515}"),
				funcI("{44BBA848-CC51-11CF-AAFA-00AA00B6015C}"),
				funcI("{9381D8F2-0288-11D0-9501-00AA00B911A5}"),
				funcI("{4F216970-C90C-11D1-B5C7-0000F8051515}"),
				funcI("{5A8D6EE0-3E18-11D0-821E-444553540000}"),
				funcI("{89820200-ECBD-11CF-8B85-00AA005B4383}"),
				funcI("{08B0E5C0-4FCB-11CF-AAA5-00401C608555}"),
				funcI("{45EA75A0-A269-11D1-B5BF-0000F8051515}"),
				funcI("{DE5AED00-A4BF-11D1-9948-00C04F98BBC9}"),
				funcI("{22D6F312-B0F6-11D0-94AB-0080C74C7E95}"),
				funcI("{44BBA842-CC51-11CF-AAFA-00AA00B6015B}"),
				funcI("{3AF36230-A269-11D1-B5BF-0000F8051515}"),
				funcI("{44BBA840-CC51-11CF-AAFA-00AA00B6015C}"),
				funcI("{CC2A9BA0-3BDD-11D0-821E-444553540000}"),
				funcI("{08B0E5C0-4FCB-11CF-AAA5-00401C608500}"),
				"Mozilla",//navigator.appCodeName
				"Netscape",//navigator.appName
				"5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.46 Safari/537.36",//navigator.appVersion
				"20030107",
				"undefined",//"navigator.browserLanguage"	
				"true",//"navigator.cookieEnabled"
				"",//"navigator.oscpu", "navigator.cpuClass"
				"true",//"navigator.onLine"
				"Win32",//"navigator.platform"
				"undefined",//"navigator.systemLanguage"\
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.46 Safari/537.36",//"navigator.userAgent"
				"en-US",//"navigator.language", "navigator.userLanguage"
				"windows-1252",//"document.defaultCharset
				"",//"document.domain"
				"undefined",//"screen.deviceXDPI"
				"undefined",//"screen.deviceYDPI"
				"undefined",//"screen.fontSmoothingEnabled"
				"undefined",//"screen.updateInterval"
				"false",//Math.abs(p - q) !== 0
				"false",// C(c)
				"@UTC@",//return "@UTC@"
				"8",//Timezone. Derived by timezone offset. -(c.getTimezoneOffset() + j) / 60
				new LocalDateTime(2005, 6, 7, 21, 33, 44, 888).toString("M/d/yyyy, h:m:s a"),//"2005/6/7%20%u4E0B%u53489%3A33%3A44",// (new Date(2005, 5, 7, 21, 33, 44, 888)).toLocaleString()				
				"1920",//"screen.width"
				"1080",//"screen.height"
				"",// n.Acrobat
				"19.0",//n.Flash
				"",//n.QuickTime
				"",//"Java Plug-in"
				"",//n.Director
				"",//n.Office
				"@CT@",//return "@CT@"
				"-480",//p timezone offset.
				"-480",//q timezone offset.
				LocalDateTime.now().toString("M/d/yyyy, h:m:s a"),//c.toLocaleString()
				"24",//"screen.colorDepth"
				"1920",//"window.screen.availWidth"
				"1040",//"window.screen.availHeight"
				"0",//"window.screen.availLeft"
				"0",//"window.screen.availTop")
				"",//g("Acrobat")
				"",//g("Adobe SVG")																																					,
				"",//g("Authorware")
				"",//g("Citrix ICA")
				"",// g("Director")
				"Shockwave Flash|Shockwave Flash 19.0 r0",//g("Flash")
				"",//g("MapGuide")
				"",//g("MetaStream")
				"",//g("PDFViewer")
				"",//g("QuickTime")
				"",//g("RealOne")
				"",//g("RealPlayer Enterprise")
				"",//g("RealPlayer Plugin")
				"",//g("Seagate Software Report")
				"",//g("Silverlight")
				"",//g("Windows Media")
				"",//g("iPIX")
				"",//g("nppdf.so")
				"20",
				/*										
				 * function() {
				 * var j = document.createElement("span");
														j.innerHTML = "&nbsp;";
														j.style.position = "absolute";
														j.style.left = "-9999px";
														document.body.appendChild(j);
														var r = j.offsetHeight;
														document.body.removeChild(j);
														return r
				 */

				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"",//k()
				"5.6.1-0",
				""//k()
		};


		for(int d=0;d<e.length;d++){
			if (a!=null) {
				f += funcV(e[d], "\"", "'", true);
				f += "=";
			}

			String h = e[d];

			f += (a!=null) ? h : excuteJS("escape('"+h+"')");
			f += ";";
			if (a!=null) f += "\n";
		}

		f = funcV(f, "@UTC@", String.valueOf(System.currentTimeMillis()), false);
		f = funcV(f, "@CT@", String.valueOf((System.currentTimeMillis()-b)), false);
		return funcW(f);
	}

	public String funcI(String a) throws UnsupportedEncodingException {
		/*
    var c = "";
    try {
        if (typeof o.a.getComponentVersion !== "undefined") c = o.a.getComponentVersion(a, "ComponentID")
    } catch (b) {
        a = b.message.length;
        a = a > 40 ? 40 : a;
        c = escape(b.message.substr(0, a))
    }
		 */


		return "";
	}

	public static String excuteJS(String str){
		ScriptEngineManager factory = new ScriptEngineManager();
		// create a JavaScript engine
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		// evaluate JavaScript code from String
		String output = null;
		try {
			output = (String) engine.eval(str);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	public String funcV(String a, String c, String b, boolean e){

		boolean f = true;

		for (int d = a.indexOf(c);d >= 0 && (e || f);d = a.indexOf(c)) {
			a = a.substring(0, d) + b + a.substring(d + c.length());
			f = false;
		}
		return a;
	}
}
