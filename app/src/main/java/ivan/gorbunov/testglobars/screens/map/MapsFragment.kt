package ivan.gorbunov.testglobars.screens.map

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import ivan.gorbunov.testglobars.R
import ivan.gorbunov.testglobars.model.retrofit.data.UnitTest
import ivan.gorbunov.testglobars.screens.autorization.AuthorizationViewModel

class MapsFragment : Fragment() {
    private lateinit var viewModel: MapsViewModel
    private lateinit var units: MutableList<UnitTest>
    private lateinit var progressBar: ProgressBar
    private lateinit var menuMy: Menu
    private lateinit var mapGoogle: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_maps, container, false)

        val viewModelFactory =
            MapsViewModelFactory(MapsFragmentArgs.fromBundle(requireArguments()).token)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapsViewModel::class.java)
        units = mutableListOf()
        progressBar = root.findViewById(R.id.progressBarMap)
        setHasOptionsMenu(true)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        viewModel.units.observe(viewLifecycleOwner, { list ->
            list.forEach { if (it.checked) units.add(it) }
            mapFragment?.getMapAsync(callback)
            updateMenu()
            progressBar.visibility = View.GONE
        })
    }

    private fun updateMenu() {
        createItemMenu(menuMy)
        onPrepareOptionsMenu(menuMy)
    }

    private val callback = OnMapReadyCallback { map ->
        mapGoogle = map
        units.forEach {
            makeMarker(map, it)
        }

        val firstPosition = LatLng(units[0].position.lt, units[0].position.ln)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPosition, 10f))
    }

    private fun makeMarker(map: GoogleMap, it: UnitTest) {
        val icons = IconGenerator(activity)
        map.addMarker(
            MarkerOptions().position(LatLng(it.position.lt, it.position.ln))
                .alpha(if (it.eye) 1F else 0.5F)
        ).setIcon(BitmapDescriptorFactory.fromBitmap(icons.makeIcon(it.name)))
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menuMy = menu
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cameraOnItem(item)
        return super.onOptionsItemSelected(item)
    }

    private fun cameraOnItem(item: MenuItem) {
        val unit = units[item.itemId]
        val position = LatLng(unit.position.lt, unit.position.ln)
        mapGoogle.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
    }


    private fun createItemMenu(menu: Menu) {
        units.forEach {
            menu.add(Menu.NONE, units.indexOf(it), Menu.NONE, it.name)
        }
    }


}