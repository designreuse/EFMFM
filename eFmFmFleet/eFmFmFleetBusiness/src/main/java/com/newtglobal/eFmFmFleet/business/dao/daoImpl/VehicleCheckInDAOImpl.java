package com.newtglobal.eFmFmFleet.business.dao.daoImpl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.business.dao.IVehicleCheckInDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmFixedDistanceContractDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripBasedContractDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractInvoicePO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractTypeMasterPO;

@SuppressWarnings("unchecked")
@Repository("IVehicleCheckInDAO")
public class VehicleCheckInDAOImpl implements IVehicleCheckInDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		entityManager.persist(eFmFmDeviceMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmDeviceMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		entityManager.remove(eFmFmDeviceMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		entityManager.persist(eFmFmVehicleMasterPO);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void vehicleDriverCheckIn(EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		entityManager.persist(eFmFmVehicleCheckInPO);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		entityManager.merge(eFmFmVehicleCheckInPO);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		entityManager.merge(eFmFmVehicleMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmEscortCheckInPO eFmFmEscortCheckInPO) {
		entityManager.merge(eFmFmEscortCheckInPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		entityManager.remove(eFmFmVehicleMasterPO);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		entityManager.merge(eFmFmEscortMasterPO);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteExtraCheckInEntry(int checkInId) {
		Query query = entityManager
				.createQuery("DELETE EFmFmVehicleCheckInPO where checkInId = '"
						+ checkInId + "' ");
		query.executeUpdate();
	}

	/**
	 * The getParticularVehicleDetails implements for Getting particular vehicle
	 * details based on vehicle Number.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-06
	 */

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getParticularDriverDetailsFromMobileNum(
			String mobileNumber, int branchId) {
		List<EFmFmDriverMasterPO> driverDetail = new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.mobileNumber = '"
						+ mobileNumber + "'  and c.branchId='" + branchId + "'");
		driverDetail = query.getResultList();
		return driverDetail;
	}

	/**
	 * The getParticularVehicleDetails implements for Getting particular vehicle
	 * details based on vehicle Number.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-06
	 */

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EFmFmVehicleMasterPO getParticularVehicleDetails(
			String vehicleNumber, int branchId) {
		List<EFmFmVehicleMasterPO> vehicleDetail = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.vehicleNumber = '"
						+ vehicleNumber
						+ "'  and c.branchId='"
						+ branchId
						+ "'");
		try {
			vehicleDetail = query.getResultList();
		} catch (Exception e) {
		}
		if (vehicleDetail.isEmpty()) {
			return null;
		} else {
			return vehicleDetail.get(0);
		}

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getVehicleDetailsFromVehicleNumber(
			String vehicleNumber, int branchId) {
		List<EFmFmVehicleMasterPO> vehicleDetail = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.vehicleNumber like '%"
						+ vehicleNumber
						+ "%'  and c.branchId='"
						+ branchId
						+ "'");
		vehicleDetail = query.getResultList();
		return vehicleDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularCheckInEntryFromDeviceVehicleDriver(
			int checkInId) {
		Query query = entityManager
				.createQuery("DELETE EFmFmVehicleCheckInPO where checkInId = '"
						+ checkInId + "' ");
		query.executeUpdate();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EFmFmVehicleMasterPO getParticularVehicleDetail(int vehicleId) {
		List<EFmFmVehicleMasterPO> vehicleDetail = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO as b  where b.vehicleId='"
						+ vehicleId + "'");
		vehicleDetail = query.getResultList();
		return vehicleDetail.get(0);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getAllVehicleDetails(
			EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		List<EFmFmVehicleMasterPO> vehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster c JOIN c.eFmFmClientBranchPO d where c.vendorId='"
						+ eFmFmVehicleMasterPO.getEfmFmVendorMaster()
								.getVendorId() + "' and b.status !='D' ");
		vehicleMasterPO = query.getResultList();
		return vehicleMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getAllDriverDetails(
			EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		List<EFmFmDriverMasterPO> driverMasterPO = new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster c JOIN c.eFmFmClientBranchPO d where c.vendorId='"
						+ eFmFmDriverMasterPO.getEfmFmVendorMaster()
								.getVendorId() + "' and b.status !='D' ");
		// Query
		// query=entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster c JOIN c.eFmFmClientBranchPO d where c.vendorId='"+eFmFmDriverMasterPO.getEfmFmVendorMaster().getVendorId()+"'  ");
		driverMasterPO = query.getResultList();
		return driverMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> randomDriverDetails(int vendorId,
			Date todayDate) {
		List<EFmFmDriverMasterPO> driverMasterPO = new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster d WHERE NOT EXISTS (SELECT c from b.efmFmVehicleCheckIns c where DATE(c.checkOutTime) is null AND c.status='Y') and d.vendorId='"
						+ vendorId + "' AND  b.status='A' ");
		driverMasterPO = query.getResultList();
		return driverMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EFmFmVehicleCheckInPO getCheckInDriverDetails(int deviceId) {
		List<EFmFmVehicleCheckInPO> driverMasterPO = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d where d.vehicleId='"
						+ deviceId + "' ORDER BY b.checkInTime desc");
		try {
			driverMasterPO = query.getResultList();
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		if (driverMasterPO.size() > 0) {

			return driverMasterPO.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getAvailableVehicleDetails(int vendorId,
			Date todayDate) {
		List<EFmFmVehicleMasterPO> vehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();
		/*
		 * Format formatter; formatter = new SimpleDateFormat("yyyy-MM-dd");
		 */
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b  JOIN b.efmFmVendorMaster d WHERE NOT EXISTS (SELECT c from b.efmFmVehicleCheckIns c where DATE(c.checkOutTime) IS NULL) and d.vendorId='"
						+ vendorId + "' AND b.status='A'");
		vehicleMasterPO = query.getResultList();
		return vehicleMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetails(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		/*
		 * Format formatter; formatter = new SimpleDateFormat("yyyy-MM-dd");
		 */
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b where b.status='Y' AND b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> checkedOutParticularDriver(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.eFmFmDeviceMaster d where  d.deviceId='"
						+ eFmFmVehicleCheckInPO.geteFmFmDeviceMaster()
								.getDeviceId()
						+ "' AND b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDriverDetails(
			int branchId, int driverId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmDriverMaster d JOIN d.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where  d.driverId='"
						+ driverId
						+ "'AND c.branchId='"
						+ branchId
						+ "' AND b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDeviceDetails(
			int branchId, int deviceId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.eFmFmDeviceMaster d JOIN d.eFmFmClientBranchPO c where  d.deviceId='"
						+ deviceId
						+ "'AND c.branchId='"
						+ branchId
						+ "' AND b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getLastCheckedOutInDeviceDetails(
			int branchId, int deviceId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.eFmFmDeviceMaster d JOIN d.eFmFmClientBranchPO c where  d.deviceId='"
						+ deviceId
						+ "'AND c.branchId='"
						+ branchId
						+ "' AND b.checkOutTime is not null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInVehicles(
			int branchId, int vehicleId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where  d.vehicleId='"
						+ vehicleId
						+ "' AND c.branchId='"
						+ branchId
						+ "' AND b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDriver(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmDriverMaster d where  d.driverId='"
						+ eFmFmVehicleCheckInPO.getEfmFmDriverMaster()
								.getDriverId()
						+ "' AND b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetails(int branchId,
			Date todayDate) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		/*
		 * Format formatter; formatter = new SimpleDateFormat("yyyy-MM-dd");
		 */
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where b.status='Y' and g.branchId='"
						+ branchId + "' and b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetailsForEditBucket(
			int branchId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where b.status='Y'  and g.branchId='"
						+ branchId + "' and b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getAllCheckedInVehiclesByVendor(
			int branchId, int vendorId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.status='Y' and c.branchId='"
						+ branchId
						+ "' and v.vendorId='"
						+ vendorId
						+ "' and  b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getAllCheckedInDriversByVendor(
			int branchId, int vendorId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmDriverMaster d JOIN d.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.status='Y' and c.branchId='"
						+ branchId
						+ "' and v.vendorId='"
						+ vendorId
						+ "' and  b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getAssignedVehicleDetails(int branchId,
			Date todayDate) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		/*
		 * Format formatter; formatter = new SimpleDateFormat("yyyy-MM-dd");
		 */
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where g.branchId='"
						+ branchId + "' and b.status='N' ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEscortMasterPO> getAllEscortDetails(
			EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		List<EFmFmEscortMasterPO> escortMasterPO = new ArrayList<EFmFmEscortMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmEscortMasterPO b  where b.isActive='Y'");
		escortMasterPO = query.getResultList();
		return escortMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEscortMasterPO> getParticularEscortDetails(
			EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		List<EFmFmEscortMasterPO> escortMasterPO = new ArrayList<EFmFmEscortMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmEscortMasterPO b  where b.escortId='"
						+ eFmFmEscortMasterPO.getEscortId()
						+ "' and b.isActive='Y'");
		escortMasterPO = query.getResultList();
		return escortMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		entityManager.persist(eFmFmEscortMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getParticulaCheckedInVehicleDetails(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b where b.checkInId='"
						+ eFmFmVehicleCheckInPO.getCheckInId() + "'");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetailsFromChecInId(
			int checkInId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b where b.checkInId='"
						+ checkInId + "'");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int getNumberOfVehiclesFromClientId(int branchId) {
		// List <EFmFmVehicleMasterPO> vehicleDetail = new
		// ArrayList<EFmFmVehicleMasterPO>();
		String vehicleDetail = entityManager
				.createQuery(
						"SELECT count(b) FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where b.status='A' OR b.status='allocated' AND g.branchId='"
								+ branchId + "' ").getSingleResult().toString();
		// vehicleDetail=query.getResultList();
		return Integer.valueOf(vehicleDetail);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEscortCheckInPO> getAllCheckedInEscort(int branchId,
			Date todayDate) {
		List<EFmFmEscortCheckInPO> escortMasterPO = new ArrayList<EFmFmEscortCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmEscortCheckInPO b JOIN b.eFmFmEscortMaster e JOIN e.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.escortCheckOutTime is null AND c.branchId='"
						+ branchId + "' AND b.status='Y'");
		escortMasterPO = query.getResultList();
		return escortMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEscortMasterPO> getAllCheckInEscort(int branchId,
			Date todayDate) {
		List<EFmFmEscortMasterPO> escortMasterPO = new ArrayList<EFmFmEscortMasterPO>();
		/*
		 * Format formatter; formatter = new SimpleDateFormat("yyyy-MM-dd");
		 */
		Query query = entityManager
				.createQuery("SELECT m FROM EFmFmEscortMasterPO m  JOIN m.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c WHERE NOT EXISTS (SELECT d from m.eFmFmEscortCheckIn d where d.escortCheckOutTime is null) AND c.branchId='"
						+ branchId + "' AND m.isActive='Y'");
		escortMasterPO = query.getResultList();
		return escortMasterPO;
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 * public List<EFmFmEscortMasterPO> getAllCheckInEscort(int branchId,Date
	 * todayDate) { List <EFmFmEscortMasterPO> escortMasterPO = new
	 * ArrayList<EFmFmEscortMasterPO>(); Format formatter; formatter = new
	 * SimpleDateFormat("yyyy-MM-dd"); Query query=entityManager.createQuery(
	 * "SELECT m FROM EFmFmEscortMasterPO m  JOIN m.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c WHERE NOT EXISTS (SELECT d from m.eFmFmEscortCheckIn d where c.branchId='"
	 * +branchId+"' AND m.isActive='Y'"); escortMasterPO=query.getResultList();
	 * return escortMasterPO; }
	 */

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveEscortCheckIn(EFmFmEscortCheckInPO eFmFmEscortCheckInPO) {
		entityManager.persist(eFmFmEscortCheckInPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDeviceMasterPO> deviceNumberExistsCheck(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		List<EFmFmDeviceMasterPO> deviceDetail = new ArrayList<EFmFmDeviceMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDeviceMasterPO b JOIN b.eFmFmClientBranchPO c where b.mobileNumber='"
						+ eFmFmDeviceMasterPO.getMobileNumber()
						+ "' AND c.branchId='"
						+ eFmFmDeviceMasterPO.geteFmFmClientBranchPO()
								.getBranchId() + "' ");
		deviceDetail = query.getResultList();
		return deviceDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDeviceMasterPO> getDeviceDetailsFromDeviceId(int deviceId,
			int branchId) {
		List<EFmFmDeviceMasterPO> deviceDetail = new ArrayList<EFmFmDeviceMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDeviceMasterPO b JOIN b.eFmFmClientBranchPO c where b.deviceId='"
						+ deviceId + "' AND c.branchId='" + branchId + "' ");
		deviceDetail = query.getResultList();
		return deviceDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDeviceMasterPO> deviceImeiNumberExistsCheck(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		List<EFmFmDeviceMasterPO> deviceDetail = new ArrayList<EFmFmDeviceMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDeviceMasterPO b JOIN b.eFmFmClientBranchPO c where b.imeiNumber='"
						+ eFmFmDeviceMasterPO.getImeiNumber()
						+ "' AND c.branchId='"
						+ eFmFmDeviceMasterPO.geteFmFmClientBranchPO()
								.getBranchId() + "'");
		deviceDetail = query.getResultList();
		return deviceDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDeviceMasterPO> getListOfAllActiveDevices(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		List<EFmFmDeviceMasterPO> deviceDetail = new ArrayList<EFmFmDeviceMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDeviceMasterPO b JOIN b.eFmFmClientBranchPO c where  c.branchId='"
						+ eFmFmDeviceMasterPO.geteFmFmClientBranchPO()
								.getBranchId() + "'");
		deviceDetail = query.getResultList();
		return deviceDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDeviceMasterPO> getAllApprovedDevices(int branchId) {
		List<EFmFmDeviceMasterPO> deviceDetail = new ArrayList<EFmFmDeviceMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDeviceMasterPO b JOIN b.eFmFmClientBranchPO c where  c.branchId='"
						+ branchId
						+ "' AND b.isActive='Y' AND b.status='Y' OR b.status='N'");
		deviceDetail = query.getResultList();
		return deviceDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmTripAlertsPO> getGroupByDriver(int branchId,
			Date fromDate, Date toDate) {
		List<EFmFmTripAlertsPO> eFmFmTripAlertsPO = new ArrayList<EFmFmTripAlertsPO>();
		Query query = entityManager
				.createQuery("SELECT t FROM EFmFmTripAlertsPO t JOIN t.efmFmAssignRoute a JOIN a.efmFmVehicleCheckIn v JOIN v.efmFmDriverMaster d JOIN a.eFmFmClientBranchPO c where c.branchId='"
						+ branchId + "' group by d.driverId ");
		eFmFmTripAlertsPO = query.getResultList();
		return eFmFmTripAlertsPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmTripAlertsPO> getScoreCardDriver(int branchId,
			int driverId, Date fromDate, Date toDate) {
		List<EFmFmTripAlertsPO> eFmFmTripAlertsPO = new ArrayList<EFmFmTripAlertsPO>();
		Query query = entityManager
				.createQuery("SELECT t FROM EFmFmTripAlertsPO t JOIN t.efmFmAssignRoute a JOIN a.efmFmVehicleCheckIn v JOIN v.efmFmDriverMaster d JOIN a.eFmFmClientBranchPO c where c.branchId='"
						+ branchId + "' and d.driverId='" + driverId + "'");
		eFmFmTripAlertsPO = query.getResultList();
		return eFmFmTripAlertsPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getDriverAssignedTrip(int branchId,
			int driverId, Date fromDate, Date toDate) {
		List<EFmFmAssignRoutePO> eFmFmAssignRoutePO = new ArrayList<EFmFmAssignRoutePO>();
		Query query = entityManager
				.createQuery("SELECT a FROM EFmFmAssignRoutePO a JOIN a.efmFmVehicleCheckIn v JOIN v.efmFmDriverMaster d JOIN a.eFmFmClientBranchPO c where c.branchId='"
						+ branchId + "' and d.driverId='" + driverId + "'");
		eFmFmAssignRoutePO = query.getResultList();
		return eFmFmAssignRoutePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmTripAlertsPO> getGroupByVehicle(int branchId,
			Date fromDate, Date toDate) {
		List<EFmFmTripAlertsPO> eFmFmTripAlertsPO = new ArrayList<EFmFmTripAlertsPO>();
		Query query = entityManager
				.createQuery("SELECT t FROM EFmFmTripAlertsPO t JOIN t.efmFmAssignRoute a JOIN a.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster d JOIN a.eFmFmClientBranchPO c where c.branchId='"
						+ branchId + "' group by d.vehicleId ");
		eFmFmTripAlertsPO = query.getResultList();
		return eFmFmTripAlertsPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmTripAlertsPO> getScoreCardVehicle(int branchId,
			int driverId, Date fromDate, Date toDate) {
		List<EFmFmTripAlertsPO> eFmFmTripAlertsPO = new ArrayList<EFmFmTripAlertsPO>();
		Query query = entityManager
				.createQuery("SELECT t FROM EFmFmTripAlertsPO t JOIN t.efmFmAssignRoute a JOIN a.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster d JOIN a.eFmFmClientBranchPO c where c.branchId='"
						+ branchId + "' and d.vehicleId='" + driverId + "'");
		eFmFmTripAlertsPO = query.getResultList();
		return eFmFmTripAlertsPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getVehicleAssignedVehicleTrip(int branchId,
			int driverId, Date fromDate, Date toDate) {
		List<EFmFmAssignRoutePO> eFmFmAssignRoutePO = new ArrayList<EFmFmAssignRoutePO>();
		Query query = entityManager
				.createQuery("SELECT a FROM EFmFmAssignRoutePO a JOIN a.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster d JOIN a.eFmFmClientBranchPO c where c.branchId='"
						+ branchId + "' and d.vehicleId='" + driverId + "'");
		eFmFmAssignRoutePO = query.getResultList();
		return eFmFmAssignRoutePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getRcNumberDetails(String rcNumber,
			int branchId) {
		List<EFmFmVehicleMasterPO> vehicleDetail = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.registartionCertificateNumber = '"
						+ rcNumber + "'  and c.branchId='" + branchId + "'");
		vehicleDetail = query.getResultList();
		return vehicleDetail;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getEngineNoDetails(String engineNo,
			int branchId) {
		List<EFmFmVehicleMasterPO> vehicleDetail = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.vehicleEngineNumber = '"
						+ engineNo + "'  and c.branchId='" + branchId + "'");
		vehicleDetail = query.getResultList();
		return vehicleDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getAllApprovedVehiclesByVendorId(
			int vendorId, int branchId) {
		List<EFmFmVehicleMasterPO> vehicleDetail = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where v.vendorId = '"
						+ vendorId
						+ "'  AND c.branchId='"
						+ branchId
						+ "' AND b.status='A' ");
		vehicleDetail = query.getResultList();
		return vehicleDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getAllApprovedDriversByVendorId(
			int vendorId, int branchId) {
		List<EFmFmDriverMasterPO> driverDetails = new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where v.vendorId = '"
						+ vendorId
						+ "'  AND c.branchId='"
						+ branchId
						+ "' AND b.status='A' ");
		driverDetails = query.getResultList();
		return driverDetails;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEscortMasterPO> getMobileNoDetails(
			EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		List<EFmFmEscortMasterPO> escortMasterPO = new ArrayList<EFmFmEscortMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmEscortMasterPO b  where b.mobileNumber='"
						+ eFmFmEscortMasterPO.getMobileNumber()
						+ "' and b.isActive='Y'");
		escortMasterPO = query.getResultList();
		return escortMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getAllActiveVehicleDetails(int vendorId) {
		List<EFmFmVehicleMasterPO> nonCheckInVehicles = new ArrayList<EFmFmVehicleMasterPO>();
		List<EFmFmVehicleMasterPO> checkInVehicles = new ArrayList<EFmFmVehicleMasterPO>();
		List<EFmFmVehicleMasterPO> allVehicles = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b  JOIN b.efmFmVendorMaster d WHERE NOT EXISTS (SELECT c from b.efmFmVehicleCheckIns c JOIN c.eFmFmDeviceMaster n JOIN c.efmFmDriverMaster m where b.status='A' and m.status='A' and n.isActive='Y') and d.vendorId='"
						+ vendorId + "' AND b.status='A'");
		Query query1 = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b  JOIN b.efmFmVendorMaster d WHERE EXISTS (SELECT c from b.efmFmVehicleCheckIns c JOIN c.eFmFmDeviceMaster n JOIN c.efmFmDriverMaster m where b.status='A' and m.status='A' and n.isActive='Y' and c.status='Y' and (c.status='Y' and c.checkOutTime is  null) OR (c.status='N' and c.checkOutTime is not null)) and d.vendorId='"
						+ vendorId + "' AND b.status='A'");
		nonCheckInVehicles = query.getResultList();
		checkInVehicles = query1.getResultList();
		allVehicles.addAll(nonCheckInVehicles);
		allVehicles.addAll(checkInVehicles);
		return allVehicles;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getAllActiveDriverDetails(int vendorId) {
		List<EFmFmDriverMasterPO> nonCheckInDrivers = new ArrayList<EFmFmDriverMasterPO>();
		List<EFmFmDriverMasterPO> checkInDrivers = new ArrayList<EFmFmDriverMasterPO>();
		List<EFmFmDriverMasterPO> allDrivers = new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster d WHERE NOT EXISTS (SELECT c from b.efmFmVehicleCheckIns c JOIN c.eFmFmDeviceMaster n JOIN c.efmFmVehicleMaster m where b.status='A' and m.status='A' and n.isActive='Y' ) and d.vendorId='"
						+ vendorId + "' AND  b.status='A' ");
		Query query1 = entityManager
				.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster d WHERE EXISTS (SELECT c from b.efmFmVehicleCheckIns c JOIN c.eFmFmDeviceMaster n JOIN c.efmFmVehicleMaster m where b.status='A' and m.status='A' and n.isActive='Y' and (c.status='Y' and c.checkOutTime is  null) OR (c.status='N' and c.checkOutTime is not null) ) and d.vendorId='"
						+ vendorId + "' AND  b.status='A' ");

		nonCheckInDrivers = query.getResultList();
		checkInDrivers = query1.getResultList();
		allDrivers.addAll(nonCheckInDrivers);
		allDrivers.addAll(checkInDrivers);
		return allDrivers;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDeviceMasterPO> getAllActiveDeviceDetails(int branchId) {
		List<EFmFmDeviceMasterPO> nonCheckInDevice = new ArrayList<EFmFmDeviceMasterPO>();
		List<EFmFmDeviceMasterPO> checkInDevice = new ArrayList<EFmFmDeviceMasterPO>();
		List<EFmFmDeviceMasterPO> allDevices = new ArrayList<EFmFmDeviceMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDeviceMasterPO b JOIN b.eFmFmClientBranchPO d WHERE NOT EXISTS (SELECT c from b.efmFmVehicleCheckIns c JOIN c.eFmFmDeviceMaster n JOIN c.efmFmVehicleMaster m where b.status='A' and m.status='A' and n.isActive='Y' ) and d.branchId='"
						+ branchId + "' and  b.status='Y' and b.isActive='Y' ");
		Query query1 = entityManager
				.createQuery("SELECT b FROM EFmFmDeviceMasterPO b JOIN b.eFmFmClientBranchPO d WHERE EXISTS (SELECT c from b.efmFmVehicleCheckIns c JOIN c.eFmFmDeviceMaster n JOIN c.efmFmVehicleMaster m where b.status='A' and m.status='A' and n.isActive='Y' and (c.status='Y' and c.checkOutTime is  null) OR (c.status='N' and c.checkOutTime is not null)) and d.branchId='"
						+ branchId + "' and  b.status='Y' and b.isActive='Y' ");

		nonCheckInDevice = query.getResultList();
		checkInDevice = query1.getResultList();
		allDevices.addAll(nonCheckInDevice);
		allDevices.addAll(checkInDevice);
		return allDevices;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getCheckInDetails(int branchId) {
		List<EFmFmVehicleCheckInPO> driverMasterPO = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT  distinct(v.vehicleId) FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"
						+ branchId + "' AND b.checkOutTime is not null ");
		List<Integer> resultList = query.getResultList();
		for (Integer listOfVehicleIds : resultList) {
			EFmFmVehicleMasterPO eFmFmVehicleMasterPO = new EFmFmVehicleMasterPO();
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO = new EFmFmVehicleCheckInPO();
			int vehicleId = listOfVehicleIds;
			eFmFmVehicleMasterPO.setVehicleId(vehicleId);
			eFmFmVehicleCheckInPO.setEfmFmVehicleMaster(eFmFmVehicleMasterPO);
			driverMasterPO.add(eFmFmVehicleCheckInPO);
		}
		return driverMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getLastCheckInEntitiesDetails(
			int branchId) {
		List<EFmFmVehicleCheckInPO> checkInDetail = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT  b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"
						+ branchId
						+ "' AND b.checkOutTime is not null  AND v.vehicleId=63");
		/*
		 * Query
		 * query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b "
		 * +
		 * "JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c "
		 * + "where c.branchId='"+branchId+"' and v.vehicleId in " +
		 * "(SELECT  distinct(v.vehicleId) " +
		 * "FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster d "
		 * + "JOIN d.eFmFmClientBranchPO c JOIN b.efmFmDriverMaster m " +
		 * " where m.driverId in (SELECT  distinct(k.driverId) FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster "
		 * +
		 * " d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmDriverMaster k  JOIN b.eFmFmDeviceMaster h where c.branchId='"
		 * +branchId+"' " +
		 * " and h.deviceId in (SELECT  distinct(g.deviceId) FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster d "
		 * +
		 * "JOIN d.eFmFmClientBranchPO c JOIN b.eFmFmDeviceMaster g where c.branchId='"
		 * +branchId+"'))) and b.checkOutTime is not null");
		 */
		checkInDetail = query.getResultList();
		return checkInDetail;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getDriverCheckInDetails(int branchId) {
		List<EFmFmVehicleCheckInPO> driverMasterPO = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT  distinct(v.driverId) FROM EFmFmVehicleCheckInPO b JOIN b.efmFmDriverMaster v JOIN v.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"
						+ branchId + "' AND b.checkOutTime is not null ");
		List<Integer> resultList = query.getResultList();
		for (Integer listOfVehicleIds : resultList) {
			EFmFmDriverMasterPO eFmFmVehicleMasterPO = new EFmFmDriverMasterPO();
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO = new EFmFmVehicleCheckInPO();
			int vehicleId = listOfVehicleIds;
			eFmFmVehicleMasterPO.setDriverId(vehicleId);
			eFmFmVehicleCheckInPO.setEfmFmDriverMaster(eFmFmVehicleMasterPO);
			driverMasterPO.add(eFmFmVehicleCheckInPO);
		}
		return driverMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> alreadyCheckInExist(int vehicleId) {
		List<EFmFmVehicleCheckInPO> driverMasterPO = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where v.vehicleId='"
						+ vehicleId
						+ "' AND b.checkOutTime is not null order by b.checkOutTime desc ");
		driverMasterPO = query.getResultList();
		return driverMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> alreadyCheckInDriverExistence(
			int driverId) {
		List<EFmFmVehicleCheckInPO> driverMasterPO = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmDriverMaster v JOIN v.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where v.driverId='"
						+ driverId
						+ "' AND b.checkOutTime is not null order by b.checkOutTime asc ");
		driverMasterPO = query.getResultList();
		return driverMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> alreadyCheckInDeviceExistence(
			int deviceId, int branchId) {
		List<EFmFmVehicleCheckInPO> driverMasterPO = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.eFmFmDeviceMaster d.eFmFmClientBranchPO c where d.deviceId='"
						+ deviceId
						+ "' AND c.branchId='"
						+ branchId
						+ "' AND b.checkOutTime is not null order by b.checkOutTime asc ");
		driverMasterPO = query.getResultList();
		return driverMasterPO;
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 * public List<EFmFmAssignRoutePO> getVendorBasedVehicleDetails(Date
	 * fromDate, Date toDate, int branchId,int vendorId, String contractType,
	 * int contractDetailsId) { List <EFmFmAssignRoutePO> eFmFmAssignRoutePO =
	 * new ArrayList<EFmFmAssignRoutePO>(); Format month; month = new
	 * SimpleDateFormat("MM"); Format year; year = new SimpleDateFormat("yyyy");
	 * Query query=entityManager.createQuery(
	 * "SELECT b FROM EFmFmAssignRoutePO b  JOIN b.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN a.eFmFmVendorContractTypeMaster c JOIN d.eFmFmClientBranchPO f WHERE d.vendorId='"
	 * +vendorId+"' AND b.tripStatus='completed' AND f.branchId='"+branchId+
	 * "' AND month(b.tripStartTime)='"
	 * +month.format(fromDate)+"' and year(b.tripStartTime)='"
	 * +year.format(fromDate
	 * )+"' AND c.contractType='"+contractType+"' and a.contractDetailId='"
	 * +contractDetailsId+"' group by a.vehicleId");
	 * eFmFmAssignRoutePO=query.getResultList(); return eFmFmAssignRoutePO;
	 * 
	 * }
	 */

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getAllDriverDetails(int branchId) {
		List<EFmFmDriverMasterPO> driverMasterPO = new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster d WHERE NOT EXISTS (SELECT c from b.efmFmVehicleCheckIns c where DATE(c.checkOutTime) is null) and d.status='A' AND  b.status='A' ");
		driverMasterPO = query.getResultList();
		return driverMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEscortCheckInPO> getParticulaEscortDetailFromEscortId(
			int branchId, int escortCheckInId) {
		List<EFmFmEscortCheckInPO> escortMasterPO = new ArrayList<EFmFmEscortCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmEscortCheckInPO b JOIN b.eFmFmEscortMaster e JOIN e.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.escortCheckOutTime is null AND c.branchId='"
						+ branchId
						+ "'  AND b.escortCheckInId='"
						+ escortCheckInId + "' ");
		escortMasterPO = query.getResultList();
		return escortMasterPO;
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 * public List<EFmFmVendorContractInvoicePO> getInvoiceforVehicle(Date
	 * fromDate, Date toDate, int branchId, int vehicleId) { List
	 * <EFmFmVendorContractInvoicePO> eFmFmVendorContractInvoicePO = new
	 * ArrayList<EFmFmVendorContractInvoicePO>(); Format month; month = new
	 * SimpleDateFormat("MM"); Format year; year = new SimpleDateFormat("yyyy");
	 * Query query=entityManager.createQuery(
	 * "SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE a.vehicleId='"
	 * +
	 * vehicleId+"' AND f.branchId='"+branchId+"' AND month(b.invoiveStartDate)='"
	 * +month.format(fromDate)+"' and year(b.invoiveStartDate)='"+year.format(
	 * fromDate)+"'"); eFmFmVendorContractInvoicePO=query.getResultList();
	 * return eFmFmVendorContractInvoicePO; }
	 */

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 * public List<EFmFmVendorContractInvoicePO> getInvoiceDetailsVendor(Date
	 * fromDate, Date toDate, int branchId, int vendorId) { List
	 * <EFmFmVendorContractInvoicePO> eFmFmVendorContractInvoicePO = new
	 * ArrayList<EFmFmVendorContractInvoicePO>(); Format month; month = new
	 * SimpleDateFormat("MM"); Format year; year = new SimpleDateFormat("yyyy");
	 * Query query=entityManager.createQuery(
	 * "SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.eFmFmVendorContractTypeMaster j JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE d.vendorId='"
	 * +
	 * vendorId+"' AND f.branchId='"+branchId+"' AND month(b.invoiveStartDate)='"
	 * +month.format(fromDate)+"' and year(b.invoiveStartDate)='"+year.format(
	 * fromDate)+"'"); eFmFmVendorContractInvoicePO=query.getResultList();
	 * return eFmFmVendorContractInvoicePO; }
	 * 
	 * @Override
	 * 
	 * @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 * public List<EFmFmVendorContractInvoicePO>
	 * getvendorInvoiceFixedDistanceSummary(Date fromDate, Date toDate, int
	 * branchId, int vendorId) { List <EFmFmVendorContractInvoicePO>
	 * eFmFmVendorContractInvoicePO = new
	 * ArrayList<EFmFmVendorContractInvoicePO>(); Format month; month = new
	 * SimpleDateFormat("MM"); Format year; year = new SimpleDateFormat("yyyy");
	 * Query query=entityManager.createQuery(
	 * "SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE d.vendorId='"
	 * +
	 * vendorId+"' AND f.branchId='"+branchId+"' AND month(b.invoiveStartDate)='"
	 * +month.format(fromDate)+"' and year(b.invoiveStartDate)='"+year.format(
	 * fromDate)+"'"); eFmFmVendorContractInvoicePO=query.getResultList();
	 * return eFmFmVendorContractInvoicePO; }
	 */

	/* Invoice */

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getVendorBasedTripSheet(Date fromDate,
			Date toDate, int branchId, int vendorId) {
		List<EFmFmVehicleMasterPO> eFmFmVehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT SUM(a.odometerEndKm - a.odometerStartKm),COUNT(b.vehicleId),b.contractDetailId,c.contractTypeId,c.contractDescription,c.contractType FROM EFmFmVehicleMasterPO b  JOIN b.efmFmVendorMaster d JOIN b.efmFmVehicleCheckIns v JOIN v.efmFmAssignRoutes a JOIN b.eFmFmVendorContractTypeMaster c JOIN d.eFmFmClientBranchPO f WHERE d.vendorId='"
						+ vendorId
						+ "' AND a.tripStatus='completed' AND f.branchId='"
						+ branchId
						+ "' AND month(a.tripStartTime)='"
						+ month.format(fromDate)
						+ "' and year(a.tripStartTime)='"
						+ year.format(fromDate)
						+ "' group by c.contractTypeId ");
		List<Object[]> resultList = query.getResultList();
		for (Object[] result : resultList) {
			EFmFmVehicleMasterPO vehicleMasterPO = new EFmFmVehicleMasterPO();
			EFmFmVendorContractTypeMasterPO contractTypeMasterPO = new EFmFmVendorContractTypeMasterPO();
			vehicleMasterPO.setSumTravelledDistance(Integer.valueOf(result[0]
					.toString()));
			vehicleMasterPO.setNoOfVehicles(Integer.valueOf(result[1]
					.toString()));
			vehicleMasterPO.setContractDetailId(Integer.valueOf(result[2]
					.toString()));
			contractTypeMasterPO.setContractTypeId(Integer.valueOf(result[3]
					.toString()));
			contractTypeMasterPO.setContractDescription((String) result[4]);
			vehicleMasterPO
					.seteFmFmVendorContractTypeMaster(contractTypeMasterPO);
			vehicleMasterPO.setContractType((String) result[5]);
			eFmFmVehicleMasterPO.add(vehicleMasterPO);
		}
		return eFmFmVehicleMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmFixedDistanceContractDetailPO> getFixedDistanceDetails(
			EFmFmFixedDistanceContractDetailPO eFmFmFixedDistanceContractDetailPO) {
		List<EFmFmFixedDistanceContractDetailPO> fixedDistanceContractDetailPO = new ArrayList<EFmFmFixedDistanceContractDetailPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmFixedDistanceContractDetailPO b JOIN b.eFmFmClientBranchPO c where b.distanceContractId='"
						+ eFmFmFixedDistanceContractDetailPO
								.getDistanceContractId()
						+ "' AND c.branchId='"
						+ eFmFmFixedDistanceContractDetailPO
								.geteFmFmClientBranchPO().getBranchId() + "'");
		fixedDistanceContractDetailPO = query.getResultList();
		return fixedDistanceContractDetailPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmTripBasedContractDetailPO> getTripDistanceDetails(
			EFmFmTripBasedContractDetailPO eFmFmTripBasedContractDetailPO) {
		List<EFmFmTripBasedContractDetailPO> tripBasedContractDetailPO = new ArrayList<EFmFmTripBasedContractDetailPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmTripBasedContractDetailPO b JOIN b.eFmFmClientBranchPO c where b.tripBasedContractId='"
						+ eFmFmTripBasedContractDetailPO
								.getTripBasedContractId()
						+ "' AND c.branchId='"
						+ eFmFmTripBasedContractDetailPO
								.geteFmFmClientBranchPO().getBranchId() + "'");
		tripBasedContractDetailPO = query.getResultList();
		return tripBasedContractDetailPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getVendorBasedVehicleDetails(Date fromDate,
			Date toDate, int branchId, int vendorId, String contractType,
			int contractDetailsId) {
		List<EFmFmAssignRoutePO> eFmFmAssignRoutePO = new ArrayList<EFmFmAssignRoutePO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmAssignRoutePO b  JOIN b.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN a.eFmFmVendorContractTypeMaster c JOIN d.eFmFmClientBranchPO f WHERE d.vendorId='"
						+ vendorId
						+ "' AND b.tripStatus='completed' AND f.branchId='"
						+ branchId
						+ "' AND month(b.tripStartTime)='"
						+ month.format(fromDate)
						+ "' and year(b.tripStartTime)='"
						+ year.format(fromDate)
						+ "' AND c.contractType='"
						+ contractType
						+ "' and a.contractDetailId='"
						+ contractDetailsId + "' group by a.vehicleId");
		eFmFmAssignRoutePO = query.getResultList();
		return eFmFmAssignRoutePO;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getTripBasedVehicleDetails(Date fromDate,
			Date toDate, int branchId, int vendorId, String contractType,
			int contractDetailsId) {
		List<EFmFmAssignRoutePO> eFmFmAssignRoutePO = new ArrayList<EFmFmAssignRoutePO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmAssignRoutePO b  JOIN b.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN a.eFmFmVendorContractTypeMaster c JOIN d.eFmFmClientBranchPO f WHERE d.vendorId='"
						+ vendorId
						+ "' AND b.tripStatus='completed' AND f.branchId='"
						+ branchId
						+ "' AND month(b.tripStartTime)='"
						+ month.format(fromDate)
						+ "' and year(b.tripStartTime)='"
						+ year.format(fromDate)
						+ "' AND c.contractType='"
						+ contractType
						+ "' and a.contractDetailId='"
						+ contractDetailsId + "'");
		eFmFmAssignRoutePO = query.getResultList();
		return eFmFmAssignRoutePO;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getNoOfWorkingDays(Date fromDate,
			Date toDate, int branchId, int vehicleId, String contractType,
			int contractDetailId) {
		List<EFmFmVehicleMasterPO> eFmFmVehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmAssignRoutePO b  JOIN b.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN a.eFmFmVendorContractTypeMaster c JOIN d.eFmFmClientBranchPO f WHERE a.vehicleId='"
						+ vehicleId
						+ "' AND b.tripStatus='completed' AND f.branchId='"
						+ branchId
						+ "' AND month(b.tripStartTime)='"
						+ month.format(fromDate)
						+ "' and year(b.tripStartTime)='"
						+ year.format(fromDate)
						+ "' AND c.contractType='"
						+ contractType
						+ "' and a.contractDetailId='"
						+ contractDetailId + "' group by b.tripStartTime");
		eFmFmVehicleMasterPO = query.getResultList();
		return eFmFmVehicleMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getSumOfTotalKmByVehicle(Date fromDate,
			Date toDate, int branchId, int vehicleId, String contractType,
			int contractDetailId) {
		List<EFmFmVehicleMasterPO> eFmFmVehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT SUM((b.odometerEndKm - b.odometerStartKm)),count(a.vehicleId) FROM EFmFmAssignRoutePO b  JOIN b.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN a.eFmFmVendorContractTypeMaster c JOIN d.eFmFmClientBranchPO f WHERE a.vehicleId='"
						+ vehicleId
						+ "' AND b.tripStatus='completed' AND f.branchId='"
						+ branchId
						+ "' AND month(b.tripStartTime)='"
						+ month.format(fromDate)
						+ "' and year(b.tripStartTime)='"
						+ year.format(fromDate)
						+ "' AND c.contractType='"
						+ contractType
						+ "' and a.contractDetailId='"
						+ contractDetailId + "'");
		List<Object[]> resultList = query.getResultList();
		for (Object[] result : resultList) {
			EFmFmVehicleMasterPO vehicleMasterPO = new EFmFmVehicleMasterPO();
			vehicleMasterPO.setSumTravelledDistance(Integer.valueOf(result[0]
					.toString()));
			vehicleMasterPO.setNoOfVehicles((Integer.valueOf(result[1]
					.toString())));
			eFmFmVehicleMasterPO.add(vehicleMasterPO);
		}
		return eFmFmVehicleMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getVehicleBasedTripSheet(Date fromDate,
			Date toDate, int branchId, int vehicleId) {
		List<EFmFmAssignRoutePO> eFmFmAssignRoutePO = new ArrayList<EFmFmAssignRoutePO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmAssignRoutePO b  JOIN b.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN a.eFmFmVendorContractTypeMaster c JOIN d.eFmFmClientBranchPO f WHERE a.vehicleId='"
						+ vehicleId
						+ "' AND b.tripStatus='completed' AND f.branchId='"
						+ branchId
						+ "' AND month(b.tripStartTime)='"
						+ month.format(fromDate)
						+ "' and year(b.tripStartTime)='"
						+ year.format(fromDate)
						+ "' order by c.contractType,b.tripStartTime");
		eFmFmAssignRoutePO = query.getResultList();
		return eFmFmAssignRoutePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getTripBasedNoOfWorkingDays(
			Date fromDate, Date toDate, int branchId, int vehicleId,
			String contractType, int contractDetailId) {
		List<EFmFmVehicleMasterPO> eFmFmVehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT SUM(a.odometerEndKm - a.odometerStartKm),COUNT(a.tripStartTime) FROM EFmFmVehicleMasterPO b  JOIN b.efmFmVendorMaster d JOIN b.efmFmVehicleCheckIns v JOIN v.efmFmAssignRoutes a JOIN b.eFmFmVendorContractTypeMaster c JOIN d.eFmFmClientBranchPO f WHERE b.vehicleId='"
						+ vehicleId
						+ "' AND a.tripStatus='completed' AND f.branchId='"
						+ branchId
						+ "' AND month(a.tripStartTime)='"
						+ month.format(fromDate)
						+ "' and year(a.tripStartTime)='"
						+ year.format(fromDate)
						+ "' AND c.contractType='"
						+ contractType
						+ "' and b.contractDetailId='"
						+ contractDetailId + "' group by a.tripStartTime");
		List<Object[]> resultList = query.getResultList();
		for (Object[] result : resultList) {
			EFmFmVehicleMasterPO vehicleMasterPO = new EFmFmVehicleMasterPO();
			vehicleMasterPO.setSumTravelledDistance(Integer.valueOf(result[0]
					.toString()));
			vehicleMasterPO
					.setNoOfDays((Integer.valueOf(result[1].toString())));
			eFmFmVehicleMasterPO.add(vehicleMasterPO);
		}
		return eFmFmVehicleMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmVendorContractInvoicePO eFmFmVendorContractInvoicePO) {
		// TODO Auto-generated method stub
		entityManager.persist(eFmFmVendorContractInvoicePO);
	}

	@Override
	public void update(EFmFmVendorContractInvoicePO eFmFmVendorContractInvoicePO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmVendorContractInvoicePO);
	}

	@Override
	public void delete(EFmFmVendorContractInvoicePO eFmFmVendorContractInvoicePO) {
		// TODO Auto-generated method stub
		entityManager.remove(eFmFmVendorContractInvoicePO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVendorContractInvoicePO> getInvoiceforVendor(
			Date fromDate, Date toDate, int branchId, int vendorId,
			String invoiceType) {
		List<EFmFmVendorContractInvoicePO> eFmFmVendorContractInvoicePO = new ArrayList<EFmFmVendorContractInvoicePO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE d.vendorId='"
						+ vendorId
						+ "' AND f.branchId='"
						+ branchId
						+ "' AND month(b.invoiveStartDate)='"
						+ month.format(fromDate)
						+ "' and year(b.invoiveStartDate)='"
						+ year.format(fromDate)
						+ "' group by a.vehicleId order by b.invoiceType");
		eFmFmVendorContractInvoicePO = query.getResultList();
		return eFmFmVendorContractInvoicePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getAllVehicleDetails(int branchId) {
		List<EFmFmVehicleMasterPO> vehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleMasterPO b  JOIN b.efmFmVendorMaster d WHERE NOT EXISTS (SELECT c from b.efmFmVehicleCheckIns c where DATE(c.checkOutTime) IS NULL) and d.status='A' AND b.status='A'");
		vehicleMasterPO = query.getResultList();
		return vehicleMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVendorContractInvoicePO> getInvoiceByVehicleFixedDistance(
			Date fromDate, Date toDate, int branchId, int vendorId,
			int vehicleId) {
		List<EFmFmVendorContractInvoicePO> eFmFmVendorContractInvoicePO = new ArrayList<EFmFmVendorContractInvoicePO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE a.vehicleId='"
						+ vehicleId
						+ "' AND f.branchId='"
						+ branchId
						+ "' AND month(b.invoiveStartDate)='"
						+ month.format(fromDate)
						+ "' and year(b.invoiveStartDate)='"
						+ year.format(fromDate)
						+ "' group by a.vehicleId order by b.invoiceType");
		eFmFmVendorContractInvoicePO = query.getResultList();
		return eFmFmVendorContractInvoicePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVendorContractInvoicePO> getInvoiceTripBasedVehicle(
			Date fromDate, Date toDate, int branchId, int vehicleId) {
		List<EFmFmVendorContractInvoicePO> eFmFmVendorContractInvoicePO = new ArrayList<EFmFmVendorContractInvoicePO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE a.vehicleId='"
						+ vehicleId
						+ "' AND f.branchId='"
						+ branchId
						+ "' AND month(b.invoiveStartDate)='"
						+ month.format(fromDate)
						+ "' and year(b.invoiveStartDate)='"
						+ year.format(fromDate) + "' order by r.tripStartTime");
		eFmFmVendorContractInvoicePO = query.getResultList();
		return eFmFmVendorContractInvoicePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVendorContractInvoicePO> getInvoiceforVendorByGroup(
			Date fromDate, Date toDate, int branchId, int vendorId) {
		List<EFmFmVendorContractInvoicePO> eFmFmVendorContractInvoicePO = new ArrayList<EFmFmVendorContractInvoicePO>();
		Format month;
		month = new SimpleDateFormat("MM");
		Format year;
		year = new SimpleDateFormat("yyyy");
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE d.vendorId='"
						+ vendorId
						+ "' AND f.branchId='"
						+ branchId
						+ "' AND month(b.invoiveStartDate)='"
						+ month.format(fromDate)
						+ "' and year(b.invoiveStartDate)='"
						+ year.format(fromDate) + "' group by b.invoiceType");
		eFmFmVendorContractInvoicePO = query.getResultList();
		return eFmFmVendorContractInvoicePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVendorContractInvoicePO> getListOfInvoiceNumbers(
			int branchId) {
		List<EFmFmVendorContractInvoicePO> eFmFmVendorContractInvoicePO = new ArrayList<EFmFmVendorContractInvoicePO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.eFmFmClientBranchPO f WHERE f.branchId='"
						+ branchId + "' group by b.invoiceNumber ");
		eFmFmVendorContractInvoicePO = query.getResultList();
		return eFmFmVendorContractInvoicePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVendorContractInvoicePO> getInvoiceDetails(int branchId,
			int InvoiceNumber) {
		List<EFmFmVendorContractInvoicePO> eFmFmVendorContractInvoicePO = new ArrayList<EFmFmVendorContractInvoicePO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE b.invoiceNumber='"
						+ InvoiceNumber
						+ "' AND f.branchId='"
						+ branchId
						+ "' group by a.vehicleId order by b.invoiceType");
		// Query
		// query=entityManager.createQuery("SELECT b FROM EFmFmVendorContractInvoicePO b  JOIN b.efmFmAssignRoute r JOIN  r.efmFmVehicleCheckIn g JOIN g.efmFmVehicleMaster a JOIN a.efmFmVendorMaster d JOIN b.eFmFmClientBranchPO f WHERE d.vendorId='"+vendorId+"' AND f.branchId='"+branchId+"' AND month(b.invoiveStartDate)='"+month.format(fromDate)+"' and year(b.invoiveStartDate)='"+year.format(fromDate)+"' group by a.vehicleId order by b.invoiceType");
		eFmFmVendorContractInvoicePO = query.getResultList();
		return eFmFmVendorContractInvoicePO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVendorContractTypeMasterPO> getContractTypeDetails(
			String contractType, int branchId) {
		List<EFmFmVendorContractTypeMasterPO> eFmFmVendorContractTypeMasterPO = new ArrayList<EFmFmVendorContractTypeMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVendorContractTypeMasterPO b  JOIN b.eFmFmClientBranchPO f WHERE f.branchId='"
						+ branchId
						+ "' and TRIM(UPPER(b.contractType))='"
						+ contractType + "' ");
		eFmFmVendorContractTypeMasterPO = query.getResultList();
		return eFmFmVendorContractTypeMasterPO;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVendorContractTypeMasterPO> getAllContractType(int branchId) {
		List<EFmFmVendorContractTypeMasterPO> eFmFmVendorContractTypeMasterPO = new ArrayList<EFmFmVendorContractTypeMasterPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVendorContractTypeMasterPO b  JOIN b.eFmFmClientBranchPO f WHERE f.branchId='"
						+ branchId + "' and b.contractStatus='Y'");
		eFmFmVendorContractTypeMasterPO = query.getResultList();
		return eFmFmVendorContractTypeMasterPO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public long getAllCheckedInVehicleCount(int branchId) {
		Query query = entityManager
				.createQuery("SELECT count(b) FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where b.status='Y' and g.branchId='"
						+ branchId + "' and b.checkOutTime is null ");
		long allCheckedInVehicleCount = (long) query.getSingleResult();
		return allCheckedInVehicleCount;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getCheckedInVehiclesOnRoad(int branchId) {
		List<EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Query query = entityManager
				.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where b.status='N' and g.branchId='"
						+ branchId + "' and b.checkOutTime is null ");
		vehicleCheckIn = query.getResultList();
		return vehicleCheckIn;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getAllActualVehicleDetailsFromBranchId(int branchId) {
		List<EFmFmVehicleMasterPO> vehicleDetail=new ArrayList<EFmFmVehicleMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.vehicleNumber NOT LIKE '% DUMMY %'  AND c.branchId='"+branchId+"' AND b.status='A' ");
		vehicleDetail=query.getResultList();	
		return vehicleDetail;	
	}
	

}
