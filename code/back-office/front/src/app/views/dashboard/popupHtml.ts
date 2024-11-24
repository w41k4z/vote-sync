import { AdministrativeDivisionStats } from '../../dto/administrative-division-stats';
import { NumberFormat } from '../../services/util/number-format';

export let getPopupHtml = (divisionStats: AdministrativeDivisionStats) => {
  let abstentionRateValue =
    100 - (divisionStats.registered / divisionStats.voters) * 100;
  let abstentionRate = NumberFormat.format(abstentionRateValue);
  let abstentionRateColor = 'text-success';
  if (50 < abstentionRateValue && abstentionRateValue < 75) {
    abstentionRateColor = 'text-warning';
  } else if (75 < abstentionRateValue) {
    abstentionRateColor = 'text-danger';
  }
  let alertsColor = '';
  if (divisionStats.alerts > 10) {
    alertsColor = 'text-danger';
  }
  let popupHtml = '<div class="custom-popup">';
  popupHtml += '<table>';
  popupHtml += '<thead >';
  popupHtml += '<tr>';
  popupHtml += '<th scope="col">' + divisionStats.divisionName + '</th>';
  popupHtml += '<th scope="col"></th>';
  popupHtml += '</tr>';
  popupHtml += '</thead>';
  popupHtml += '<tbody>';
  popupHtml += '<tr>';
  popupHtml += '<td>Electeurs</td>';
  popupHtml += `<td class="text-end">${NumberFormat.format(
    divisionStats.voters
  )}</td>`;
  popupHtml += '</tr>';
  popupHtml += '<tr>';
  popupHtml += '<td>Abstention</td>';
  popupHtml += `<td class="text-end ${abstentionRateColor}">${abstentionRate}%</td>`;
  popupHtml += '</tr>';
  popupHtml += '<tr>';
  popupHtml += '<td>Couverture</td>';
  popupHtml += `<td class="text-end"><b>${NumberFormat.format(
    divisionStats.collectedResults - divisionStats.importedResults
  )}</b>/${NumberFormat.format(divisionStats.totalPollingStationCount)}</td>`;
  popupHtml += '</tr>';
  popupHtml += '<tr>';
  popupHtml += '<td>Incidences</td>';
  popupHtml += `<td class="text-end ${alertsColor}">${NumberFormat.format(
    divisionStats.alerts
  )}</td>`;
  popupHtml += '</tr>';
  popupHtml += '</tbody>';
  popupHtml += '</table>';
  popupHtml += '<div class="mt-2 d-flex justify-content-center">';
  popupHtml += `<button class="btn btn-sm btn-outline-secondary w-100" id="details-button">Détails</button>`;
  popupHtml += '</div>';
  popupHtml += '</div>';
  return popupHtml;
};
