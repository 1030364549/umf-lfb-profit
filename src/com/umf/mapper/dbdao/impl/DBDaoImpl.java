package com.umf.mapper.dbdao.impl;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umf.mapper.dbbase.BaseDao;
import com.umf.mapper.dbdao.DBDao;
import com.umf.utils.DateUtil;
import com.umf.utils.RunningData;

public class DBDaoImpl extends BaseDao implements DBDao {
	
	@Override
	public boolean selAgNat() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = conMan.getConnection();
			/*根据交易类型是否为H007，返回sql*/
			sql = getIsChannelSql();
			pstmt = conn.prepareStatement(sql);
			/*根据交易类型替换sql中的*/
			if ("H007".equals(RunningData.getMsgtype())) {
				/*若类型是H007，根据终端号posno查询：代理商性质 0-经销商、1-渠道商、2-机构*/
				pstmt.setString(1, RunningData.getPosno());
			} else {
				/*若不是，则根据商户编号merno查询*/
				pstmt.setString(1, RunningData.getMerno());
			}
			rs = pstmt.executeQuery();
			String agNa = "0";
			if (rs.next()) {
				agNa = rs.getString("agent_nature");
			}
			/*判断代理商性质是否为机构*/
			return "2".equals(agNa) ? true : false;
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn, pstmt, rs);
		}
	}
	
	@Override
	public boolean selSerial(String isChannel) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = conMan.getConnection();
			sql = getSelSerialSql(isChannel);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, RunningData.getSerial());
			rs = pstmt.executeQuery();
			int serial = 0;
			if (rs.next()) {
				serial = rs.getInt("serial");
			}
			return serial == 0 ? true : false;
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn, pstmt, rs);
		}
	}

	@Override
	public Map<String, String> selAuditdata() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		Map<String, String> keyMap = null;
		try {
			conn = conMan.getConnection();
			sql = " select au.*,decode(ra.ar_type,'','',ra.ar_type) as aff_type,decode(ra.ar_type,0,substr(ra.ar_content,3),ra.ar_content) as aff_content from ( " + 
				  " select au.serial,au.posno,au.localdate,au.localtime,au.merno,au.mer_category,au.sn,fp.agent_num,au.cardtype,decode(au.msgtype,'H007',0,'H302',2,'H201',4,'H202',3) as msgtype,decode(substr(au.inputtype,0,2),'07',0,'05',1,'02',2) as inputtype,au.isdissmis," + 
				  " au.isysf,au.sett_type,au.amount,au.charge,au.rate_code,au.affix_charge,au.rate_affix,au.pan,au.bno,au.tmoney,au.rrno,au.phonepay,mi.mer_shortname,mi.ld_merno,nvl(mi.vip_status,'0000-00-00') as vip_status," + 
				  " ai.agent_name,fp.pos_type,ra.ar_type,decode(ra.ar_type,0,substr(ra.ar_content,3),ra.ar_content) as ar_content from auditdata au" + 
				  " left join formal_pos fp on fp.posno = au.posno left join agent_info ai on ai.agent_num = fp.agent_num left join merchant_info mi on mi.merno = au.merno left join rate_algorithm ra on ra.ar_mark = au.rate_code" + 
				  " where au.serial = ? and au.msgtype = ? and au.merno = ? and is_profit = ? and au.amount between ra.onset_money and ra.end_money" + 
				  " ) au left join rate_algorithm ra on ra.ar_mark = au.rate_affix";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, RunningData.getSerial());
			pstmt.setString(2, RunningData.getMsgtype());
			pstmt.setString(3, RunningData.getMerno());
			pstmt.setString(4, "0");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				keyMap = new HashMap<String, String>();
				keyMap.put("serial", rs.getString("serial"));
				keyMap.put("posno", rs.getString("posno"));
				keyMap.put("localdate", rs.getString("localdate"));
				keyMap.put("localtime", rs.getString("localtime"));
				keyMap.put("merno", rs.getString("merno"));
				keyMap.put("mer_category",rs.getString("mer_category"));
				keyMap.put("sn", rs.getString("sn"));
				keyMap.put("agent_num", rs.getString("agent_num"));
				keyMap.put("cardtype", rs.getString("cardtype"));
				keyMap.put("msgtype", rs.getString("msgtype"));
				keyMap.put("inputtype", rs.getString("inputtype"));
				keyMap.put("isdissmis", rs.getString("isdissmis"));
				keyMap.put("isysf", rs.getString("isysf"));
				keyMap.put("sett_type", rs.getString("sett_type"));
				keyMap.put("amount", rs.getString("amount"));
				keyMap.put("charge", rs.getString("charge"));
				keyMap.put("rate_code", rs.getString("rate_code"));
				keyMap.put("affix_charge", rs.getString("affix_charge"));
				keyMap.put("rate_affix", rs.getString("rate_affix"));
				keyMap.put("pan", rs.getString("pan"));
				keyMap.put("bno", rs.getString("bno"));
				keyMap.put("tmoney", rs.getString("tmoney"));
				keyMap.put("rrno", rs.getString("rrno"));
				keyMap.put("phonepay", rs.getString("phonepay"));
				keyMap.put("mer_shortname", rs.getString("mer_shortname"));
				keyMap.put("ld_merno", rs.getString("ld_merno"));
				keyMap.put("vip_status", rs.getString("vip_status"));
				keyMap.put("agent_name", rs.getString("agent_name"));
				keyMap.put("pos_type", rs.getString("pos_type"));
				keyMap.put("ar_type", rs.getString("ar_type"));
				keyMap.put("ar_content", rs.getString("ar_content"));
				keyMap.put("aff_type", rs.getString("aff_type"));
				keyMap.put("aff_content", rs.getString("aff_content"));
			}
			return keyMap;
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		}finally {
			conMan.closeCon(conn, pstmt, rs);
		}
	}

	@Override
	public List<Map<String, String>> selAgdata(String agent_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = conMan.getConnection();
			sql = " select aa.agent_num,aa.agent_name,aa.agent_level,aa.isgradually,nvl(bb.agent_num,'0') as lower_agent_num,nvl(bb.agent_name,'0') as lower_agent_name from ( " + 
				  " select a.agent_num,a.agent_name,a.agent_level,a.belong_agent,a.isgradually from agent_info a start with a.agent_num = ? connect by prior a.belong_agent = a.agent_num ) aa " + 
				  " left join ( select b.agent_num, b.agent_name, b.agent_level, b.belong_agent from agent_info b start with b.agent_num = ? connect by prior b.belong_agent = b.agent_num ) bb on aa.agent_num = bb.belong_agent order by aa.agent_level desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, agent_num);
			pstmt.setString(2, agent_num);
			rs = pstmt.executeQuery();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> tradMap = new HashMap<String, String>();
				tradMap.put("agent_num", rs.getString("agent_num"));
				tradMap.put("agent_name", rs.getString("agent_name"));
				tradMap.put("agent_level", rs.getString("agent_level"));
				tradMap.put("isgradually", rs.getString("isgradually"));
				tradMap.put("lower_agent_num", rs.getString("lower_agent_num"));
				tradMap.put("lower_agent_name", rs.getString("lower_agent_name"));
				list.add(tradMap);
			}
			return list;
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn, pstmt, rs);
		}
	}

	@Override
	public String isAct(String localdate) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = conMan.getConnection();
			sql = "select act_id from act_active where act_id in (select act_id from branch_pos where posno = ? ) and end_time >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, RunningData.getPosno());
			pstmt.setString(2, localdate);
			rs = pstmt.executeQuery();
			String act_id = "";
			if (rs.next()) {
				act_id = rs.getObject(1).toString();
			}
			return act_id;
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn, pstmt, rs);
		}
	}

	@Override
	public Map<String, Map<String, String>> selPoldata(Map<String, String> audMap) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = conMan.getConnection();
			sql = " select adr.agent_id,substr(afd.settlement_price,3) as sett_price,afd.ceiling_cost as ceiling_cost,substr(afd.surcharge,3) as surcharge,afd.surcharge_ein as surcharge_ein,tp.tax_point" + 
			      " from (select agent_id,decode(?,0,decode(?,0,ct_pip_divided_id,1,ct_act_divided_id),1,decode(?,0,pip_divided_id,1,act_divided_id,2,vip_divided_id)) as divided_id from agent_divided_rule" + 
			      " where agent_id in (select agent_num from agent_info start with agent_num = ? connect by prior belong_agent = agent_num ) " + 
			      " ) adr left join act_fprice_detailed afd on afd.fprice_rules_id = adr.divided_id left join tax_point tp on tp.agent_num = adr.agent_id where afd.distribution_type = ? and afd.card_type = ? and afd.settlement_type = ? and profit_mark = ? and tp.type = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, audMap.get("pos_type"));
			pstmt.setString(2, audMap.get("proMark"));
			pstmt.setString(3, audMap.get("proMark"));
			pstmt.setString(4, audMap.get("agent_num"));
			pstmt.setString(5, audMap.get("msgtype"));
			if ("0".equals(audMap.get("msgtype")) && "1".equals(audMap.get("isysf"))) {
				pstmt.setString(5, "1");
			}
			pstmt.setString(6, audMap.get("cardtype"));
			pstmt.setString(7, audMap.get("sett_type"));
			pstmt.setString(8, audMap.get("proMark"));
			pstmt.setString(9, audMap.get("pos_type"));
			rs = pstmt.executeQuery();
			Map<String, Map<String, String>> tradMap = new HashMap<String, Map<String, String>>();
			while (rs.next()) {
				Map<String, String> rowData = new HashMap<String, String>();
				rowData.put("agent_id", rs.getString("agent_id"));			/*用户id*/
				rowData.put("sett_price", rs.getString("sett_price"));		/*状态*/
				rowData.put("ceiling_cost", rs.getString("ceiling_cost"));	
				rowData.put("surcharge", rs.getString("surcharge"));
				rowData.put("surcharge_ein", rs.getString("surcharge_ein"));
				rowData.put("tax_point", rs.getString("tax_point"));
				tradMap.put(rs.getString("agent_id".toUpperCase()) + "", rowData);
			}
			return tradMap;
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn, pstmt, rs);
		}
	}
	
	@Override
	public int insProData(Map<String, String> auMap, Map<String, String> agMap) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "insert into profit_detailed (id,serial,localdate,localtime,merno,mer_name,mer_category,pos_sn,activity,agent_num,agent_name,belong_agent,belong_agent_name," + 
				  "card_type,pay_type,accept_type,double_exemption,quick_pay,settle_method,amount,mer_rate,surcharge_rate,mer_charge,surcharge_charge," + 
				  "is_capping,settle_ratio,surcharge_ratio,charge_profit,surcharge_profit,createdate,createtime,createman,activity_name,pan,rrno,bno,money," + 
				  "profit_agent_num,profit_agent_name,posno,charge_profit_below,surcharge_profit_below,agent_level,isgradually,level_tax,below_tax,level_tax_profit,below_tax_profit,profit_tax_diff,tax,pos_type,ld_merno) values (seq_profit_detailed.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			conn = conMan.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, auMap.get("serial"));
			pstmt.setString(2, auMap.get("localdate"));
			pstmt.setString(3, auMap.get("localtime"));
			pstmt.setString(4, auMap.get("merno"));
			pstmt.setString(5, auMap.get("mer_shortname"));
			pstmt.setString(6, auMap.get("isVip"));   //  商户类别 0普通商户 1 vip商户
			pstmt.setString(7, auMap.get("sn"));
			pstmt.setString(8, auMap.get("isAct")); // 活动
			pstmt.setString(9, auMap.get("agent_num")); // 交易代理编号
			pstmt.setString(10, auMap.get("agent_name")); // 交易代理名称
			pstmt.setString(11, agMap.get("lower_agent_num")); // 下级代理编号 （分润代理编号的下级代理编号）
			pstmt.setString(12, agMap.get("lower_agent_name")); // 下级代理名称
			pstmt.setString(13, auMap.get("cardtype"));
			pstmt.setString(14, auMap.get("msgtype"));
			pstmt.setString(15, auMap.get("inputtype")); // 受理方式
			if("1".equals(auMap.get("phonepay"))) {
				pstmt.setString(15, "3"); // 受理方式
			}
			pstmt.setString(16, auMap.get("isdissmis")); // 小额双免标识
			pstmt.setString(17, auMap.get("isysf")); // 云闪付标识
			pstmt.setString(18, auMap.get("sett_type"));
			pstmt.setString(19, auMap.get("amount"));
			pstmt.setString(20, auMap.get("rate_code"));
			pstmt.setString(21, auMap.get("rate_affix"));  // 附加费率
			pstmt.setString(22, auMap.get("charge"));		// 手续费
			pstmt.setString(23, auMap.get("affix_charge"));	//附加费
			pstmt.setString(24, auMap.get("ar_type"));
			pstmt.setString(25, auMap.get("ar_type").equals("0")?auMap.get("sett_price"):auMap.get("ceiling_cost"));
			pstmt.setString(26, auMap.get("aff_type") == null ? "" : auMap.get("aff_type").equals("0")?auMap.get("surcharge"):auMap.get("surcharge_ein"));
			pstmt.setString(27, auMap.get("proMoney"));  // 商户手续费分润(本级)
			pstmt.setString(28, auMap.get("addproMoney"));  // 附加手续费分润(本级)
			String[] date = DateUtil.getGroupDateTime();
			pstmt.setString(29, date[0]); // 添加日期
			pstmt.setString(30, date[1]);
			pstmt.setString(31, "系统");
			pstmt.setString(32, auMap.get("actId"));
			pstmt.setString(33, auMap.get("pan"));
			pstmt.setString(34, auMap.get("rrno"));
			pstmt.setString(35, auMap.get("bno"));
			pstmt.setString(36, auMap.get("tmoney"));
			pstmt.setString(37, agMap.get("agent_num")); // 分润代理编号
			pstmt.setString(38, agMap.get("agent_name")); // 分润代理名称
			pstmt.setString(39, auMap.get("posno")); // 
			pstmt.setString(40, auMap.get("proSumMoney"));  // 商户手续费分润(本级及以下)
			pstmt.setString(41, auMap.get("addproSumMoney"));  // 附加手续费分润(本级及以下)
			pstmt.setString(42, agMap.get("agent_level"));  // 附加手续费分润(本级及以下)
			pstmt.setString(43, agMap.get("isgradually"));  // 是否逐级 0：开启 1：关闭
			pstmt.setString(44, auMap.get("level_tax"));  // 是否逐级 0：开启 1：关闭
			pstmt.setString(45, auMap.get("below_tax"));  // 是否逐级 0：开启 1：关闭
			pstmt.setString(46, auMap.get("level_tax_profit"));  // 是否逐级 0：开启 1：关闭
			pstmt.setString(47, auMap.get("below_tax_profit"));  // 是否逐级 0：开启 1：关闭
			pstmt.setString(48, auMap.get("profit_tax_diff"));  // 是否逐级 0：开启 1：关闭
			pstmt.setString(49, auMap.get("tax_point"));  // 是否逐级 0：开启 1：关闭
			pstmt.setString(50, auMap.get("pos_type"));  // 是否逐级 0：开启 1：关闭
			pstmt.setString(51, auMap.get("ld_merno"));  // 是否逐级 0：开启 1：关闭
			return pstmt.executeUpdate();
		} catch (Exception e) {
			errorInfo(e, sql.toString());
			throw e;
		} finally {
			conMan.closeCon(conn, pstmt);
		}
	}
	
	@Override
	public int upProData() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "update auditdata set is_profit = ? where serial = ?";
			conn = conMan.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, "1");
			pstmt.setString(2, RunningData.getSerial());
			int re = pstmt.executeUpdate();
			return re;
		} catch (Exception e) {
			errorInfo(e, sql.toString());
			throw e;
		} finally {
			conMan.closeCon(conn, pstmt);
		}
	}

	@Override
	public Map<String, String> selchanPoldata() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		Map<String, String> keyMap = null;
		try {
			conn = conMan.getConnection();
			sql = " select * from agent_related where agent_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				keyMap = new HashMap<String, String>();
				keyMap.put("serial", rs.getString("serial"));
				keyMap.put("posno", rs.getString("posno"));
				keyMap.put("localdate", rs.getString("localdate"));
			}
			return keyMap;
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn, pstmt, rs);
		}
	}

	@Override
	public BigDecimal selChaRate(Map<String,String> audMap) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try{
			conn = conMan.getConnection();
			sql =   " select decode(ar_type,'0',substr(ar_content,3),ar_content) rate" +
					" from agent_related ar left join rate_algorithm ra" +
					" on decode(?,'0',ar.rated,'1',ar.rates)=ra.ar_mark" +
					" where ar.agent_num = ? and ar.credit_type = ?" +
					" and ar.ratetype = ? and ra.ar_type = ?" +
					" and ? between ra.onset_money and ra.end_money ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,audMap.get("sett_type"));	/*结算类型 0:D0 1:T1*/
			pstmt.setString(2,audMap.get("agent_num"));	/*代理商编号*/
			pstmt.setString(3,audMap.get("cardtype"));		/*卡类型 0:未知 1:借记卡 2:贷记卡 3:预付费卡*/
			pstmt.setString(4,audMap.get("mer_category"));	/*成本费率类型 0:标准类 1:优惠类 2:减免类*/
			pstmt.setString(5,audMap.get("ar_type"));		/*类型 0:费率 1:封顶 2:借贷通用*/
			pstmt.setString(6,audMap.get("amount"));		/*交易金额*/
			rs = pstmt.executeQuery();
			rs.next();
			String rate = rs.getString("rate");
			return 	new BigDecimal(rate);
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn,pstmt,rs);
		}
	}

	@Override
	public Integer insChaData(Map<String, String> audMap) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try{
			conn = conMan.getConnection();
			sql =   " insert into channel_profit_detailed(id,serial,localdate,localtime,merno,mer_name,ratetype," +
					"pos_sn,activity,agent_num,agent_name,card_type,accept_type,double_exemption,quick_pay," +
					"settle_method,amount,mer_charge,pan,rrno,bno,money,posno,channel_cost,channel_profit) " +
					"values(seq_channel_profit_detailed.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,audMap.get("serial"));			/*交易流水号*/
			pstmt.setString(2,audMap.get("localdate"));			/*交易日期*/
			pstmt.setString(3,audMap.get("localtime"));			/*交易时间*/
			pstmt.setString(4,audMap.get("merno"));				/*商户编号*/
			pstmt.setString(5,audMap.get("mer_shortname"));		/*商户名称*/
			pstmt.setString(6,audMap.get("mer_category"));		/*成本费率类型 0:标准类 1:优惠类 2:减免类*/
			pstmt.setString(7,audMap.get("sn"));				/*终端sn*/
			pstmt.setString(8,audMap.get("isAct"));				/*活动 0:否 1:是*/
			pstmt.setString(9,audMap.get("agent_num"));			/*机构编号*/
			pstmt.setString(10,audMap.get("agent_name"));		/*机构名称*/
			pstmt.setString(11,audMap.get("cardtype"));			/*卡类型 1:借记卡 2:贷记卡*/
			pstmt.setString(12,audMap.get("inputtype"));		/*受理方式 0:挥卡 1:插卡 2:刷卡 3:手机pay*/
			pstmt.setString(13,audMap.get("isdissmis"));		/*0:普通交易 1:双免交易*/
			pstmt.setString(14,audMap.get("isysf"));			/*云闪付标识 0:否 1:是*/
			pstmt.setString(15,audMap.get("sett_type"));		/*结算方式 0:D0 1:T1*/
			pstmt.setString(16,audMap.get("amount"));			/*交易金额*/
			pstmt.setString(17,audMap.get("charge"));			/*商户手续费*/
			pstmt.setString(18,audMap.get("pan"));				/*交易卡号*/
			pstmt.setString(19,audMap.get("rrno"));				/*交易参考号*/
			pstmt.setString(20,audMap.get("bno"));				/*通道商户号*/
			pstmt.setString(21,audMap.get("tmoney"));			/*结算金额*/
			pstmt.setString(22,audMap.get("posno"));			/*系统终端号*/
			pstmt.setString(23,audMap.get("channel_cost"));		/*机构成本*/
			pstmt.setString(24,audMap.get("channel_profit"));	/*机构分润*/
			return pstmt.executeUpdate();
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn,pstmt,rs);
		}
	}

	public BigDecimal sumAmount() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = conMan.getConnection();
			sql =   " select nvl(sum(amount),0) sum_amount from auditdata " +
					" where merno = ? and posno = ? " +
					" and to_date(localdate||' '||localtime,'yyyy/mm/dd hh24:mi:ss') " +
					" between add_months(sysdate,-3) " +
					" and sysdate ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,RunningData.getMerno());
			pstmt.setString(2,RunningData.getPosno());
			rs = pstmt.executeQuery();
			rs.next();
			return new BigDecimal(rs.getString("sum_amount"));
		} catch (Exception e) {
			errorInfo(e, sql);
			throw e;
		} finally {
			conMan.closeCon(conn,pstmt,rs);
		}
	}

}
